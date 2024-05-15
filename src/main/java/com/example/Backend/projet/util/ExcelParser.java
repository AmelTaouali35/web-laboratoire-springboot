package com.example.Backend.projet.util;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Backend.projet.model.Customer;
import com.example.Backend.projet.model.Laboratoire;
import com.example.Backend.projet.repository.LaboratoireRepository;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ExcelParser {
    @Autowired
    private LaboratoireRepository laboratoireRepository;

    public List<Customer> parseExcel(InputStream inputStream , String code) throws IOException {
        List<Customer> customers = new ArrayList<>();
        Laboratoire laboratoire = 	laboratoireRepository.findByCode(code).orElseThrow();
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if (row.getRowNum() == 0) {
                    continue; // Skip header row
                }

                Customer customer = new Customer();
                customer.setCode(row.getCell(0).getStringCellValue());
                customer.setCodelabo(row.getCell(1).getStringCellValue());
                customer.setLibelle(row.getCell(2).getStringCellValue());
                customer.setStockresoff((int) row.getCell(3).getNumericCellValue());
                customer.setCm(row.getCell(4).getStringCellValue());
                customer.setMois(row.getCell(5).getStringCellValue());
                customer.setVenteresoff((int) row.getCell(6).getNumericCellValue());
                customer.setStockdepreg((int) row.getCell(7).getNumericCellValue());
                customer.setVentedepreg((int) row.getCell(8).getNumericCellValue());
                customer.setEntree((int) row.getCell(9).getNumericCellValue());
                customer.setReception((int) row.getCell(10).getNumericCellValue());
                customer.setSdouane((int) row.getCell(11).getNumericCellValue());
                customer.setAnnocee((int) row.getCell(12).getNumericCellValue());
                customer.setSoldecde((int) row.getCell(13).getNumericCellValue());
                customer.setEncours((int) row.getCell(14).getNumericCellValue());
                customer.setEtat(row.getCell(15).getStringCellValue());
                customer.setLaboratoire(laboratoire);
                
                // Set the current date
                customer.setDate(LocalDate.now());

                customers.add(customer);
            }
        }

        return customers;
    }
}
