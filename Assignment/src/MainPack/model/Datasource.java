package MainPack.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Datasource {
    public static Datasource instance = new Datasource();

    public static final String DB_NAME = "contactsmgmt.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Databases\\" + DB_NAME;

    public static final String TABLE_USERS = "users";
    public static final String USERNAME_COLUMN = "username";
    public static final String PASSWORD_COLUMN = "password";

    public static final String TABLE_CONTACTS = "contacts";
    public static final String CONTACT_ID_COLUMN = "contact_id";
    public static final String FIRSTNAME_COLUMN = "firstName";
    public static final String SURNAME_COLUMN = "surName";
    public static final String TELEPHONE_COLUMN = "telephone";

    public static final String TABLE_GROUPS = "groups";
    public static final String GROUP_ID_COLUMN = "group_id";
    public static final String GROUP_NAME_COLUMN = "groupName";

    public static final String TABLE_CONTACTSINGROUPS = "contactsInGroups";

    private Connection conn;

    private Datasource() {
    }

    public static Datasource getInstance() {
        return instance;
    }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            System.out.println("Connecting...");
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to the database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Connection was closed.");
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public boolean loginSuccessful(String username, String password) {
        this.open();
        try {
            StringBuilder sb = new StringBuilder("SELECT * FROM ");
            sb.append(TABLE_USERS);
            sb.append(" WHERE ");
            sb.append(USERNAME_COLUMN);
            sb.append("='");
            sb.append(username);
            sb.append("' AND ");
            sb.append(PASSWORD_COLUMN);
            sb.append("='");
            sb.append(password);
            sb.append("'");
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(sb.toString());
            if (results.next()) {
                results.close();
                statement.close();
                this.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error when checking credentials. " + e.getMessage());
            //this.close();
        }

        return false;
    }

    public List<Contact> queryAllContacts() {
        this.open();
        List<Contact> contacts = new ArrayList<Contact>();
        try {
            StringBuilder sb = new StringBuilder("SELECT * FROM ");
            sb.append(TABLE_CONTACTS);
            sb.append(" ORDER BY ");
            sb.append(FIRSTNAME_COLUMN);
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(sb.toString());

            while (results.next()) {
                int id = results.getInt("contact_id");
                String firstName = results.getString("firstName");
                String surName = results.getString("surName");
                String telephone = results.getString("telephone");
                Contact contact = new Contact(id, firstName, surName, telephone);
                contacts.add(contact);
            }
            this.close();
            System.out.println(contacts.toString());
        } catch (SQLException e) {
            System.out.println("Error when querying the contacts table " + e.getMessage());
        }
        return contacts;
    }


    public boolean changePassword(String username, String  newPassword){
        this.open();
        try{
            Statement statement = conn.createStatement();
            StringBuilder sb = new StringBuilder("UPDATE ");
            sb.append(TABLE_USERS);
            sb.append(" SET ");
            sb.append(PASSWORD_COLUMN);
            sb.append("='");
            sb.append(newPassword);
            sb.append("' WHERE ");
            sb.append(USERNAME_COLUMN);
            sb.append("='");
            sb.append(username);
            sb.append("'");
            statement.execute(sb.toString());
            System.out.println("Changing password for user "+username+". New password is "+newPassword.toUpperCase());
            this.close();
            return true;
        }
        catch (SQLException e){
            System.out.println("Could not change password "+ e.getMessage());
            this.close();
        }
        return false;
    }


    public void createTableUsers(){
        try {
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS users (username text, password text)");
        }
        catch (SQLException e){
            System.out.println("Table could not be created. "+e.getMessage());
        }
    }

    public void saveChangesEditContactWindow(int id, String firstName, String lastName, String phone){
        this.open();
        try{
            Statement statement = conn.createStatement();
            StringBuilder sb = new StringBuilder("UPDATE ");
            sb.append(TABLE_CONTACTS);
            sb.append(" SET ");
            sb.append(FIRSTNAME_COLUMN);
            sb.append("='");
            sb.append(firstName);
            sb.append("', ");
            sb.append(SURNAME_COLUMN);
            sb.append("='");
            sb.append(lastName);
            sb.append("', ");
            sb.append(TELEPHONE_COLUMN);
            sb.append("='");
            sb.append(phone);
            sb.append("' WHERE ");
            sb.append(CONTACT_ID_COLUMN);
            sb.append("=");
            sb.append(id);
            statement.execute(sb.toString());
            this.close();

        }catch(SQLException e){
            System.out.println("Could not save changes to this contact "+e.getMessage());
        }

    }

    public void deleteContact(int id) {
        this.open();
        try{
            Statement statement = conn.createStatement();
            StringBuilder sb = new StringBuilder("DELETE FROM ");
            sb.append(TABLE_CONTACTS);
            sb.append(" WHERE ");
            sb.append(CONTACT_ID_COLUMN);
            sb.append("=");
            sb.append(id);
            statement.execute(sb.toString());
            this.close();

        }catch(SQLException e){
            System.out.println("Could not save changes to this contact "+e.getMessage());
        }
    }

    public void addContact(String firstName, String lastName, String phone) {
        this.open();
        try{
            Statement statement = conn.createStatement();
            StringBuilder sb = new StringBuilder("INSERT INTO ");
            sb.append(TABLE_CONTACTS);
            sb.append(" (");
            sb.append(FIRSTNAME_COLUMN);
            sb.append(", ");
            sb.append(SURNAME_COLUMN);
            sb.append(", ");
            sb.append(TELEPHONE_COLUMN);
            sb.append(") VALUES ('");
            sb.append(firstName);
            sb.append("', '");
            sb.append(lastName);
            sb.append("', '");
            sb.append(phone);
            sb.append("')");

            statement.execute(sb.toString());
            this.close();

        }catch(SQLException e){
            System.out.println("Could not save changes to this contact "+e.getMessage());
        }
    }

    public void addGroup(String name){
        this.open();
        try{
            Statement statement = conn.createStatement();
            StringBuilder sb = new StringBuilder("INSERT INTO ");
            sb.append(TABLE_GROUPS);
            sb.append(" (");
            sb.append(GROUP_NAME_COLUMN);
            sb.append(") VALUES ('");
            sb.append(name);
            sb.append("')");

            statement.execute(sb.toString());
            this.close();
        }catch(SQLException e){
            System.out.println("Could not save this group "+e.getMessage());
        }
    }

    public List<Group> showAllGroups(){
        this.open();
        List<Group> groupList = new ArrayList<Group>();
        try{
            Statement statement = conn.createStatement();
            StringBuilder sb = new StringBuilder("SELECT * FROM ");
            sb.append(TABLE_GROUPS);
            ResultSet results = statement.executeQuery(sb.toString());
            while(results.next()){
                int id = results.getInt("group_id");
                String name = results.getString("groupName");
                Group tempGroup = new Group(id, name);
                groupList.add(tempGroup);
            }
            this.close();
        }catch(SQLException e){
            System.out.println("Could not save this group "+e.getMessage());
        }
        return groupList;
    }

    public List<Contact> searchContacts(String firstName, String lastName, String phone){
        this.open();
        List<Contact> foundContacts = new ArrayList<Contact>();
        try{
            Statement statement = conn.createStatement();
            StringBuilder sb = new StringBuilder("SELECT * FROM ");
            sb.append(TABLE_CONTACTS);
            sb.append(" WHERE ");
            sb.append(FIRSTNAME_COLUMN);
            sb.append(" LIKE '%");
            sb.append(firstName);
            sb.append("%' AND ");
            sb.append(SURNAME_COLUMN);
            sb.append(" LIKE '%");
            sb.append(lastName);
            sb.append("%' AND ");
            sb.append(TELEPHONE_COLUMN);
            sb.append(" LIKE '%");
            sb.append(phone);
            sb.append("%'");
            ResultSet results = statement.executeQuery(sb.toString());
            while(results.next()){
                int id = results.getInt("contact_id");
                String fName = results.getString("firstName");
                String sName = results.getString("surName");
                String tPhone = results.getString("telephone");
                Contact contact = new Contact(id, fName, sName, tPhone);
                foundContacts.add(contact);
            }
            System.out.println(foundContacts.toString());
            results.close();
            statement.close();
            this.close();
        }
        catch(SQLException e){
            System.out.println("Could not perform this search "+e.getMessage());
        }
        return foundContacts;
    }

    public Contact retrieveContact(String firstName, String lastName, String phone){
        this.open();
        List<Contact> foundContacts = new ArrayList<Contact>();
        try{
            Statement statement = conn.createStatement();
            StringBuilder sb = new StringBuilder("SELECT * FROM ");
            sb.append(TABLE_CONTACTS);
            sb.append(" WHERE ");
            sb.append(FIRSTNAME_COLUMN);
            sb.append("='");
            sb.append(firstName);
            sb.append("' AND ");
            sb.append(SURNAME_COLUMN);
            sb.append("='");
            sb.append(lastName);
            sb.append("' AND ");
            sb.append(TELEPHONE_COLUMN);
            sb.append("='");
            sb.append(phone);
            sb.append("'");
            ResultSet results = statement.executeQuery(sb.toString());
            while(results.next()){
                int id = results.getInt("contact_id");
                String fName = results.getString("firstName");
                String sName = results.getString("surName");
                String tPhone = results.getString("telephone");
                Contact contact = new Contact(id, fName, sName, tPhone);
                foundContacts.add(contact);
            }
            System.out.println(foundContacts.toString());
            results.close();
            statement.close();
            this.close();
        }
        catch(SQLException e){
            System.out.println("Could not perform this search "+e.getMessage());
        }
        return foundContacts.get(0);
    }

    public Contact retrieveContact(int id){
        this.open();
        List<Contact> foundContacts = new ArrayList<Contact>();
        try{
            Statement statement = conn.createStatement();
            StringBuilder sb = new StringBuilder("SELECT * FROM ");
            sb.append(TABLE_CONTACTS);
            sb.append(" WHERE ");
            sb.append(CONTACT_ID_COLUMN);
            sb.append("=");
            sb.append(id);

            ResultSet results = statement.executeQuery(sb.toString());
            while(results.next()){
                int cid = results.getInt("contact_id");
                String fName = results.getString("firstName");
                String sName = results.getString("surName");
                String tPhone = results.getString("telephone");
                Contact contact = new Contact(cid, fName, sName, tPhone);
                foundContacts.add(contact);
            }
            System.out.println(foundContacts.toString());
        }
        catch(SQLException e){
            System.out.println("Could not perform this search "+e.getMessage());
        }
        return foundContacts.get(0);
    }

    public List<Contact> showContactsForClickedGroup(int groupId) {
        this.open();
        List<Contact> contactList = new ArrayList<Contact>();
        try{
            Statement statement = conn.createStatement();
            StringBuilder sb = new StringBuilder("SELECT DISTINCT * FROM ");
            sb.append(TABLE_CONTACTS);
            sb.append(" JOIN ");
            sb.append(TABLE_CONTACTSINGROUPS);
            sb.append(" ON ");
            sb.append(TABLE_CONTACTS);
            sb.append(".");
            sb.append(CONTACT_ID_COLUMN);
            sb.append("=");
            sb.append(TABLE_CONTACTSINGROUPS);
            sb.append(".");
            sb.append(CONTACT_ID_COLUMN);
            sb.append(" WHERE ");
            sb.append(TABLE_CONTACTSINGROUPS);
            sb.append(".");
            sb.append(GROUP_ID_COLUMN);
            sb.append("=");
            sb.append(groupId);
            System.out.println(sb.toString());
            ResultSet results = statement.executeQuery(sb.toString());
            while(results.next()){
                int id = results.getInt("contact_id");
                String firstName = results.getString("firstName");
                String lastName = results.getString("surName");
                String telephone = results.getString("telephone");
                Contact tempContact = new Contact(id, firstName, lastName, telephone);
                contactList.add(tempContact);
                System.out.println(contactList.toString());
            }
            this.close();
        }catch(SQLException e){
            System.out.println("Could not save this group "+e.getMessage());
        }
        return contactList;
    }

    public void addContactToGroup(int contactId, int groupId){
        this.open();
        try{
            Statement statement = conn.createStatement();
            StringBuilder sb = new StringBuilder("INSERT INTO ");
            sb.append(TABLE_CONTACTSINGROUPS);
            sb.append(" VALUES (");
            sb.append(contactId);
            sb.append(", ");
            sb.append(groupId);
            sb.append(")");
            statement.execute(sb.toString());
            this.close();
            System.out.println("Executing: "+sb.toString());
        }catch(SQLException e){
            System.out.println("Could not save this group "+e.getMessage());
        }
    }
}
