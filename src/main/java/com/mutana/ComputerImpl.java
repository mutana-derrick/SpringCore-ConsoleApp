package com.mutana;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

@Component
public class ComputerImpl implements ComputerInterface {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void recordDetails(){
        System.out.print("Record a computer name:");
        String computerName = scanner.nextLine();

        if (computerName.equalsIgnoreCase("exit")){
            System.out.println("Thank you!!");
            System.exit(0);
        }

        System.out.print("Record a computer serial number:");
        String serialNumber = scanner.nextLine();


        try(Connection conn = DBConnection.getConnection()){

            String sql = "INSERT INTO computers (computer_name, serial_number, date) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1,computerName);
                pstmt.setString(2, serialNumber);
                pstmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));

                pstmt.executeUpdate( );

            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("Computer details recorded!\n.");
    }

    @Override
    public void viewRecords(){

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet result = stmt.executeQuery("SELECT * FROM computers");){

            if (!result.isBeforeFirst()) {
                System.out.println("No computers recorded.");
                return;
            }


            while (result.next()) {
                System.out.println(
                        "Name: " + result.getString("computer_name") + "\n" +
                                "Serial Number: " + result.getString("serial_number") + "\n" +
                                "Date: " + result.getDate("date") + "\n" +
                                "------------------"
                );
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving computer records: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
