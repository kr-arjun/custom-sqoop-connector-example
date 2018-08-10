# custom-sqoop-connector-example

A sample sqoop connector program to demonstrate how to override default implementation of Sqoop Default connection manager.
For instance , the default implementation of getCurTimestampQuery in Default connection manager may not compatible with certain databases.
For this ,Sqoop does provide way for users to implement custom connection managers.This program shows to register your connection manager 
with connection URLS specific to database and override default implementation of getCurTimestampQuery specific to teiid JDBC connection.

**Usage**

1) Build the custom implementation as java executable. 
2) Copy the custom implementation jar to Sqoop lib - ( For mapr Sqoop 1.4.6 /opt/mapr/sqoop/sqoop-1.4.6/lib/ )
3) Specify the '--connection-manager' option corresponding to newly created connection manager 

```
sqoop import --connection-manager org.apache.connectors.teiid.TeiidManager \
--driver org.teiid.jdbc.TeiidDriver --verbose --connect jdbc:teiid:<rest of connection details> 
--username <user> --password <password> --table test_table --hcatalog-database 
test_db --hcatalog-table test_hive_tbl
```
