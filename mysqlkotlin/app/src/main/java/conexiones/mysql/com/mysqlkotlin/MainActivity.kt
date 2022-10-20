package conexiones.mysql.com.mysqlkotlin

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.sql.*
import java.util.*
import android.R.attr.password
import android.os.Handler


class MainActivity : AppCompatActivity() {

    var conn: Connection? = null
    internal var username = "root" // provide the username
    internal var password = "root" // provide the corresponding password

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //executeMySQLQuery()
        Ass().execute()
        Ass().get()
    }

    fun executeMySQLQuery() {
        var stmt: Statement? = null
        var resultset: ResultSet? = null

        try {
            stmt = conn!!.createStatement()
            resultset = stmt!!.executeQuery("SHOW DATABASES;")

            if (stmt.execute("SHOW DATABASES;")) {
                resultset = stmt.resultSet
            }

            while (resultset!!.next()) {
                println(resultset.getString("Database"))
                a.setText(""+resultset.getString("Database"))
            }
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } finally {
            // release resources
            if (resultset != null) {
                try {
                    resultset.close()
                } catch (sqlEx: SQLException) {
                }

                resultset = null
            }

            if (stmt != null) {
                try {
                    stmt.close()
                } catch (sqlEx: SQLException) {
                }

                stmt = null
            }

            if (conn != null) {
                try {
                    conn!!.close()
                } catch (sqlEx: SQLException) {
                }

                conn = null
            }
        }
    }

    fun getConnection() {

            val connectionProps = Properties()
            connectionProps.put("user", username)
            connectionProps.put("password", password)
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance()

                conn = DriverManager.getConnection(
                        "jdbc:" + "mysql" + "://" +
                                "192.168.20.103" +
                                ":" + "3306" + "/" +
                                "",
                        connectionProps)

                if(conn == null){
                    a.setText("no se conecto")
                }
                else{
                    a.setText(" se conecto")
                }



            } catch (ex: SQLException) {
                // handle any errors
                a.setText(""+ex)

                ex.printStackTrace()
            } catch (ex: Exception) {
                // handle any errors
                a.setText(""+ex)

                ex.printStackTrace()
            }



    }

     inner class Ass: AsyncTask<String, String, String>(){
         var result: String ="";

        override fun onPreExecute() {
            super.onPreExecute()
        }

         override fun doInBackground(vararg params: String?): String {
             val connectionProps = Properties()
             connectionProps.put("user", username)
             connectionProps.put("password", password)
             try {
                 Class.forName("com.mysql.jdbc.Driver").newInstance()

                 conn = DriverManager.getConnection(
                         "jdbc:" + "mysql" + "://" +
                                 "192.168.20.103" +
                                 ":" + "3306" + "/" +
                                 "",
                         connectionProps)

                 if(conn == null){
                     result = "no se conecto"
                 }
                 else{
                     result = " se conecto"
                 }



             } catch (ex: Exception) {
                 // handle any errors

                 result = ex.toString()
                 ex.printStackTrace()
             }
             return result
         }

         override fun onPostExecute(result: String?) {
             super.onPostExecute(result)
             a.setText(""+result)
         }

    }

}

