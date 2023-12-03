import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TimeZone;

// Kelas Utama
public class MinimarketApp {
    // Informasi supermarket
    private static final String supermarketName = "RNR Mart"; // Nama supermarket yang diperbarui
    private static Date date = new Date();

    // Informasi pelanggan
    private static String customerName;
    private static String phoneNumber;
    private static String address;

    // Informasi pembelian barang
    private static String productCode;
    private static String productName;
    private static double productPrice;
    private static int quantity;
    private static double totalPayment;

    // Metode untuk menampilkan semua informasi yang terkumpul
    private static void displayAllData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss z");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta")); // Set zona waktu ke Waktu Indonesia Bagian Barat (WIB)
        timeFormat.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));

        System.out.println(supermarketName); // Menampilkan hanya "RNR Mart" tanpa tambahan teks lain
        System.out.println("Tanggal: " + dateFormat.format(date));
        System.out.println("Waktu: " + timeFormat.format(date));

        System.out.println("=====================");
        System.out.println("DATA PELANGGAN");
        System.out.println("------------------------");
        System.out.println("Nama Pelanggan: " + customerName);
        System.out.println("No. HP: " + phoneNumber);
        System.out.println("Alamat: " + address);

        System.out.println("+++++++++++++++++++++");
        System.out.println("DATA PEMBELIAN BARANG");
        System.out.println("----------------------------------");
        System.out.println("Kode Barang: " + productCode);
        System.out.println("Nama Barang: " + productName);
        System.out.println("Harga Barang: " + productPrice);
        System.out.println("Jumlah Beli: " + quantity);
        System.out.println("TOTAL BAYAR: " + totalPayment);
        System.out.println("+++++++++++++++++++++");

        System.out.println("Kasir: RNR"); // Nama kasir yang diperbarui
    }

    // Metode Utama
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Input Data Pelanggan
            System.out.print("Nama Pelanggan: ");
            customerName = scanner.next();

            System.out.print("No. HP: ");
            phoneNumber = scanner.next();

            System.out.print("Alamat: ");
            address = scanner.next();

            // Input Kode Barang
            System.out.print("Kode Barang: ");
            productCode = scanner.next();

            System.out.print("Nama Barang: ");
            productName = scanner.next();

            System.out.print("Harga Barang: ");
            productPrice = scanner.nextDouble();

            System.out.print("Jumlah Barang: ");
            quantity = scanner.nextInt();
            if (quantity <= 0) {
                throw new InvalidJumlahBarangException("Jumlah barang harus lebih dari 0.");
            }

            System.out.print("Total Bayar: ");
            totalPayment = scanner.nextDouble();

            // Contoh penggunaan kelas BarangDiskon
            BarangDiskon barangDiskon = new BarangDiskon(productName, productPrice, quantity, 0.1);
            barangDiskon.displayInfo();
            System.out.println("Total Bayar (setelah diskon): " + barangDiskon.calculateTotal());

            // Menampilkan semua informasi yang terkumpul
            displayAllData();

        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid. Pastikan input sesuai dengan tipe data yang diminta.");
        } catch (InvalidJumlahBarangException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}

// Exception untuk jumlah barang yang tidak valid
class InvalidJumlahBarangException extends Exception {
    public InvalidJumlahBarangException(String message) {
        super(message);
    }
}

// Interface untuk menghitung total bayar
interface CalculateTotal {
    double calculateTotal();
}

// Kelas dasar untuk barang
class Barang {
    protected String namaBarang;
    protected double hargaBarang;
    protected int jumlahBarang;

    public Barang(String namaBarang, double hargaBarang, int jumlahBarang) {
        this.namaBarang = namaBarang;
        this.hargaBarang = hargaBarang;
        this.jumlahBarang = jumlahBarang;
    }

    public void displayInfo() {
        System.out.println("Nama Barang: " + namaBarang);
        System.out.println("Harga Barang: " + hargaBarang);
        System.out.println("Jumlah Barang: " + jumlahBarang);
    }
}

// Kelas turunan untuk barang dengan diskon
class BarangDiskon extends Barang implements CalculateTotal {
    private double diskon;

    public BarangDiskon(String namaBarang, double hargaBarang, int jumlahBarang, double diskon) {
        super(namaBarang, hargaBarang, jumlahBarang);
        this.diskon = diskon;
    }

    @Override
    public double calculateTotal() {
        double total = hargaBarang * jumlahBarang;
        return total - (total * diskon);
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Diskon: " + (diskon * 100) + "%");
    }
}