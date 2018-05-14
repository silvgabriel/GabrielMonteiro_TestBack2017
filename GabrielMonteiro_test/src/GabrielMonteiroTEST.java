import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class GabrielMonteiroTEST {
 
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/appbatch";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
 
    public static void main(String[] argv) {
 
        try {
         Scanner ler = new Scanner(System.in);
         int opcao = 5;
         while(opcao!=1 && opcao!=2){
         System.out.printf(" Digite 1 para inserir os registros (já pronto) na tabela ou se já inseriu\n Digite 2 para efetuar o calculo da média e imprimir os clientes utilizados.\n");
         opcao = Integer.parseInt(ler.next());
         switch (opcao){
             case 1: appBatchInto();break;
             case 2: appBatchSelect();break;
        }   
        }
 
        } catch (SQLException e) {
 
            System.out.println(e.getMessage());
 
        }
 
    }
 
    private static void appBatchInto() throws SQLException {
 
        Connection dbConnect = null;
        PreparedStatement preparedStatement = null;
 
        String query = "INSERT INTO tb_customer_account"
                + "(id_customer, cpf_cnpj, nm_customer, is_active, vl_total) VALUES"
                + "(?,?,?,?,?)";
 
        try {
            dbConnect = getDBConnect();
            preparedStatement = dbConnect.prepareStatement(query);
 
            dbConnect.setAutoCommit(false);
            
            preparedStatement.setInt(1, 1543);
            preparedStatement.setInt(2, 1111111111);
            preparedStatement.setString(3, "Gabriel");
            preparedStatement.setInt(4, 1);
            preparedStatement.setDouble(5, 502.4);
            preparedStatement.addBatch();
            
            preparedStatement.setInt(1, 2600);
            preparedStatement.setInt(2, 222222222);
            preparedStatement.setString(3, "Ricardo");
            preparedStatement.setInt(4, 1);
            preparedStatement.setDouble(5, 900.3);
            preparedStatement.addBatch();
            
            preparedStatement.setInt(1, 1543);
            preparedStatement.setInt(2, 333333333);
            preparedStatement.setString(3, "Ana");
            preparedStatement.setInt(4, 1);
            preparedStatement.setDouble(5, 3898.6);
            preparedStatement.addBatch();
            
            preparedStatement.setInt(1, 1789);
            preparedStatement.setInt(2, 444444444);
            preparedStatement.setString(3, "Beatriz");
            preparedStatement.setInt(4, 1);
            preparedStatement.setDouble(5, 1765.7);
            preparedStatement.addBatch();
            
            preparedStatement.setInt(1, 1200);
            preparedStatement.setInt(2, 555555555);
            preparedStatement.setString(3, "Marcelo");
            preparedStatement.setInt(4, 0);
            preparedStatement.setDouble(5, 2987.2);
            preparedStatement.addBatch();
            
            preparedStatement.setInt(1, 2997);
            preparedStatement.setInt(2, 666666666);
            preparedStatement.setString(3, "Carolina");
            preparedStatement.setInt(4, 0);
            preparedStatement.setDouble(5, 997.4);
            preparedStatement.addBatch();
            
            preparedStatement.setInt(1, 1980);
            preparedStatement.setInt(2, 777777777);
            preparedStatement.setString(3, "Paulo");
            preparedStatement.setInt(4, 0);
            preparedStatement.setDouble(5, 2098.4);
            preparedStatement.addBatch();

            preparedStatement.executeBatch();
 
            dbConnect.commit();
 
            System.out.println("Inserido na tabela tb_user_account");
 
        } catch (SQLException e) {
 
            System.out.println("Dados já inseridos na tabela, selecione a opção 2 !");
            dbConnect.rollback();
 
        } finally {
 
            if (preparedStatement != null) {
                preparedStatement.close();
            }
 
            if (dbConnect != null) {
                dbConnect.close();
            }
 
        }
 
    }
    
 private static void appBatchSelect() throws SQLException {
        Connection dbConnect = null; 
        String query = "SELECT * FROM tb_customer_account WHERE (id_customer BETWEEN 1500 AND 2700) AND vl_total > 560 ORDER BY vl_total DESC;";
        try {
            ArrayList<Customer> listaCustomer = new ArrayList<Customer>();
            dbConnect = getDBConnect();
            dbConnect.setAutoCommit(false);
 		PreparedStatement pstm = dbConnect.prepareStatement(query);
                ResultSet rs = pstm.executeQuery();
                float media = 0;
                int cont = 0;
                while(rs.next()){ 
                                Customer newCustomer = new Customer();

                  String idcustomer= rs.getString("id_customer");
                  String cpfcnpj= rs.getString("cpf_cnpj");
                  String nome= rs.getString("nm_customer");
                  String isactive = rs.getString("is_active");
                  String vl_total= rs.getString("vl_total");
                  media = Float.parseFloat(rs.getString("vl_total"))+media;
                  newCustomer.setId_customer(idcustomer);
                  newCustomer.setCpf_cnpj(cpfcnpj);
                  newCustomer.setNm_customer(nome);
                  newCustomer.setIs_active(isactive);
                  newCustomer.setVl_total(vl_total);
                  
                  listaCustomer.add(newCustomer);
                  cont++;
                }
                
                for (int j=0; j<listaCustomer.size(); j++){
                    System.out.println("***************************************************************************************");
                    System.out.println("\n"+(j+1)+"° Cliente utilizado");
                    System.out.println(listaCustomer.get(j));
                }
                    System.out.println("***************************************************************************************");
                System.out.println("\nMédia do campo vl_total: "+ media/cont+"\n");
                System.out.println("***************************************************************************************");
		rs.close();
                pstm.close();
                dbConnect.commit();
        } catch (SQLException e) {
 
            System.out.println(e.getMessage());
            dbConnect.rollback();
 
        } finally {
 
            if (dbConnect != null) {
                dbConnect.close();
            }
 
        }
 }
 
 
 
    private static Connection getDBConnect() {
 
        Connection dbConnect = null;
 
        try {
 
            Class.forName(DB_DRIVER);
 
        } catch (ClassNotFoundException e) {
 
            System.out.println(e.getMessage());
 
        }
 
        try {
 
            dbConnect = DriverManager.getConnection(
                              DB_CONNECTION, DB_USER,DB_PASSWORD);
            return dbConnect;
 
        } catch (SQLException e) {
 
            System.out.println(e.getMessage());
 
        }
 
        return dbConnect;
 
    }

 
}