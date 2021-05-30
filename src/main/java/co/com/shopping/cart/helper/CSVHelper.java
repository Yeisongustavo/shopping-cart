package co.com.shopping.cart.helper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;

import co.com.shopping.cart.commons.ServiceException;
import co.com.shopping.cart.dto.ProductDto;
import co.com.shopping.cart.model.Product;

public class CSVHelper {
	public static String TYPE = "text/csv";
	static String[] HEADERs = { "nombre", "marca", "precio", "cantidad en stock", "estado", "porcentaje descuento" };

	public static boolean hasCSVFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	public static List<ProductDto> csvToProducts(MultipartFile file) throws ServiceException {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			List<ProductDto> products = new ArrayList<>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {
				ProductDto productDto = new ProductDto(csvRecord.get("nombre"), csvRecord.get("marca"),
						csvRecord.get("precio").isEmpty() ? null : Integer.parseInt(csvRecord.get("precio")),
						csvRecord.get("cantidad en stock").isEmpty() ? null
								: Integer.parseInt(csvRecord.get("cantidad en stock")),
						csvRecord.get("estado"), csvRecord.get("porcentaje descuento").isEmpty() ? null
								: Integer.parseInt(csvRecord.get("porcentaje descuento")));
				products.add(productDto);
			}
			return products;
		} catch (IOException e) {
			throw new ServiceException("fail to parse CSV file: " + e.getMessage());
		}
	}

	public static ByteArrayInputStream productsToCSV(List<Product> products) {
		final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
			for (Product product : products) {
				List<String> data = Arrays.asList(
//              String.valueOf(product.getId()),
//              product.getTitle(),
//              product.getDescription(),
//              String.valueOf(product.isPublished())
				);
				csvPrinter.printRecord(data);
			}
			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
		}
	}

}
