package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Utils.ConnectDb;

public class TransactionDAO {

	private Connection connection;
	
	public TransactionDAO() throws ClassNotFoundException, SQLException {
		connection = ConnectDb.connectToDb();
	}
	
	
	//CREATE transaction
	
	public void createTransaction(Transaction newTransaction) {
		
		String sql = "INSERT INTO Transactions (item_id, type, quantity, receiver_type, receiver_id, discount) VALUES (?, ?, ?, ?, ?, ?);";
		
		try(PreparedStatement stmt = connection.prepareStatement(sql);){
			
			stmt.setInt(1, newTransaction.getItemId());
			stmt.setString(2, newTransaction.getType());
            stmt.setInt(3, newTransaction.getQuantity());
            stmt.setString(4, newTransaction.getReceiverType());
            stmt.setInt(5, newTransaction.getReceiverId());
            stmt.setDouble(6, newTransaction.getDiscount());
            
            stmt.executeUpdate();
            
            System.out.println("Transction recorded successfully..");

			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//READ all transactions
	
	public ArrayList<Transaction> viewTransactions(){
		
		ArrayList <Transaction> allTransactions = new ArrayList<Transaction>();
		
		String sql = "SELECT * FROM Transactions;";
		
		try(Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql);){
			
			
			//int id, int ItemId, String Type, int Quantity, String ReceiverType, Date date, int Discount, int ReceiverId
			while(rs.next()) {
				
				Transaction thisTransaction = new Transaction(
						rs.getInt("id"),
						rs.getInt("item_id"),
						rs.getString("type"),
						rs.getInt("quantity"),
						rs.getString("receiver_type"),
						rs.getDate("date").toLocalDate(),
						rs.getInt("discount"),
						rs.getInt("receiver_id")
						);
				
				allTransactions.add(thisTransaction);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return allTransactions;
	}
	
	
	//READ transaction by id
public ArrayList<Transaction> viewTransactionById(int reveiverId){
		
		ArrayList <Transaction> transactions = new ArrayList<Transaction>();
		
		String sql = "SELECT * FROM Transactions WHERE receiver_id=?;";
		
		try(PreparedStatement stmt = connection.prepareStatement(sql);){
			
			stmt.setInt(1, reveiverId);
			ResultSet rs = stmt.executeQuery();
			
			//int id, int ItemId, String Type, int Quantity, String ReceiverType, Date date, int Discount, int ReceiverId
			while(rs.next()) {
				
				Transaction thisTransaction = new Transaction(
						rs.getInt("id"),
						rs.getInt("item_id"),
						rs.getString("type"),
						rs.getInt("quantity"),
						rs.getString("receiver_type"),
						rs.getDate("date").toLocalDate(),
						rs.getDouble("discount"),
						rs.getInt("receiver_id")
						);
				
				transactions.add(thisTransaction);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return transactions;
	}
}
