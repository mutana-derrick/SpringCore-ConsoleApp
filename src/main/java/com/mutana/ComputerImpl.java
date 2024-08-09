package com.mutana;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class ComputerImpl implements ComputerInterface {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ComputerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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

        String sql = "INSERT INTO computers (computer_name, serial_number, date) VALUES (?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, computerName, serialNumber, java.sql.Date.valueOf(LocalDate.now()));

        if (rowsAffected > 0) {
            System.out.println("Computer details recorded!\n");
        } else {
            System.out.println("Failed to record computer details.");
        }
    }

    @Override
    public void viewRecords(){

        List<Map<String, Object>> computers = jdbcTemplate.queryForList("SELECT * FROM computers");

        if (computers.isEmpty()) {
            System.out.println("No computers recorded.");
            return;
        }

        for (Map<String, Object> computer : computers) {
            System.out.println(
                    "Name: " + computer.get("computer_name") + "\n" +
                            "Serial Number: " + computer.get("serial_number") + "\n" +
                            "Date: " + computer.get("date") + "\n" +
                            "------------------"
            );
        }

    }
}

