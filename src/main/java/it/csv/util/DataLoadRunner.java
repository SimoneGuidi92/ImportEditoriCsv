package it.csv.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;

import it.csv.model.Autore;
import it.csv.model.Book;
import it.csv.model.CasaEditrice;
import it.csv.repository.AutoreRepository;
import it.csv.repository.BookRepository;
import it.csv.repository.CasaEditriceRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DataLoadRunner implements CommandLineRunner {

	@Autowired
	BookRepository bookRepo;

	@Autowired
	CasaEditriceRepository casaEditriceRepo;

	@Autowired
	AutoreRepository autoreRepo;

	@Override
	public void run(String... args) throws Exception {
		log.info("*** INIZIO IMPORT DATI ***");

		initCasaEditore();
		initAutore();
		initBook();
		initRelazioniEditori();

		log.info("*** FINE IMPORT DATI ***");

	}

	private void initRelazioniEditori() throws FileNotFoundException, IOException {
		try (CSVReader csvReader = new CSVReader(new FileReader("editori.csv"));) {
			String[] values = null;
			csvReader.readNext(); // aggiungo questa linea di codice per far si che il reader salti la prima riga
									// del file csv
			CasaEditrice casaEditrice;
			List<Book> books;
			List<Autore> autori;
			while ((values = csvReader.readNext()) != null) {

				casaEditrice = casaEditriceRepo.findById(Long.valueOf(values[0])).get();

				books = casaEditrice.getBooks();
				books.add(bookRepo.findById(Long.valueOf(values[2])).get());
				casaEditrice.setBooks(books);

				autori = casaEditrice.getAutori();
				autori.add(autoreRepo.findById(Long.valueOf(values[1])).get());
				casaEditrice.setAutori(autori);

				casaEditriceRepo.save(casaEditrice);
			}
		}
	}

	private void initCasaEditore() throws FileNotFoundException, IOException {
		try (CSVReader csvReader = new CSVReader(new FileReader("casaeditore.csv"));) {
			String[] values = null;
			csvReader.readNext(); // aggiungo questa linea di codice per far si che il reader salti la prima riga
									// del file csv
			while ((values = csvReader.readNext()) != null) {
				casaEditriceRepo.save(new CasaEditrice(values[0], values[1]));
			}
		}
	}

	private void initAutore() throws FileNotFoundException, IOException {
		try (CSVReader csvReader = new CSVReader(new FileReader("autore.csv"));) {
			String[] values = null;
			csvReader.readNext(); // aggiungo questa linea di codice per far si che il reader salti la prima riga
									// del file csv
			while ((values = csvReader.readNext()) != null) {
				autoreRepo.save(new Autore(values[0], values[1]));
			}
		}
	}

	private void initBook() throws FileNotFoundException, IOException {
		try (CSVReader csvReader = new CSVReader(new FileReader("book.csv"));) {
			String[] values = null;
			csvReader.readNext(); // aggiungo questa linea di codice per far si che il reader salti la prima riga
									// del file csv
			while ((values = csvReader.readNext()) != null) {
				bookRepo.save(new Book(values[0], values[1], values[2]));
			}
		}
	}

	// @Override
//	public void run(String... args) throws Exception {
//		
//		//List<List<String>> records = new ArrayList<List<String>>();
//		try (CSVReader csvReader = new CSVReader(new FileReader("book.csv"));) {
//		    String[] values = null;
//		    csvReader.readNext();	// aggiungo questa linea di codice per far si che il reader salti la prima riga del file csv
//		    while ((values = csvReader.readNext()) != null) {
//		        //records.add(Arrays.asList(values));
//		    	bookRepo.save(new Book(values[0],values[1]));
//		    }
//		}
//		System.out.println("TEST");
//	}

}
