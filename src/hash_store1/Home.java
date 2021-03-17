package hash_store1;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.io.FileOutputStream;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.print.PrinterJob;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JComboBox;
import javax.swing.WindowConstants;

/*
 * @author ahmed
 */
public class Home extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    public DefaultTableModel stockTable, salesTable, clientTable, purchasesTable, deptTable, consTable, orderTable, inventoryTable1, inventoryTable2, returningTable, userTable, homeTable1, homeTable2, homeTable3, homeTable4, homeTable5, homeTable6, homeTable7;
    int panalIs = 1, whatTime = 1, x;
    Instant Start, finish;
    int pane2_pressed = 0, pane3_pressed = 0, pane4_pressed = 0, pane5_pressed = 0, pane6_pressed = 0, pane7_pressed = 0, pane9_pressed = 0;
    //static String desktopPath = System.getProperty("user.home") + "\\Desktop\\";
    Process process;
    static String filePath = "E:\\Hash";
    static String barcodeDest = "E:\\Hash\\Barcode";
    static String userNameLang = "E";
    public static String reciept_header1 = " وش السعد                            ";
    public static String reciept_header2 = "     للمنظفات و مستحضرات التجميل               ";
    public static String reciept_header3 = "     الشارع التجاري                         ";
    public static String reciept_footer1 = "01226342663     وش السعد     01223356530";
    public static String reciept_footer2 = "--   صمم بواسطة شركة Hash للبرمجيات   01068322486   --";
    String Names[][] = new String[][]{
        {"Owner", "المالك"},
        {"Home", "الرئيسية"},
        {"Stock", "المخزن"},
        {"Sales", "المبيعات"},
        {"Purchases", "المدفوعات"},
        {"Depts", "الديون"},
        {"Cons", "النواقص"},
        {"Returns", "المرجوعات"},
        {"Orders", "الطلبات"},
        {"Profits", "الارباح"},
        {"Inventory", "الجرد"},
        {"Clients", "العملاء"},
        {"Users", "المستخدمين"},
        {"Help", "كيفية الاستخدام"},
        {"About", "عن البرنامج"},
        {"ID :", ": الكود "},
        {"Item :", ": الصنف "},
        {"Quantity :", ": الكمية "},
        {"Sales Price :", ": سعر البيع "},
        {"Purchase Price :", ": سعر الشراء "},
        {"Alarm Range :", ": حد التنبيه "},
        {"Price :", ": السعر "},
        {"View", "عرض"},
        {"Add", "إضافة"},
        {"Delete", "مسح"},
        {"Edit", "تعديل"},
        {"Clear", "إعادة"},
        {"Check ID :", ": رقم الشيك"},
        {"Client Name :", ": اسم العميل"},
        {"Mobile :", ": الموبايل"},
        {"Total :", ": التكلفة"},
        {"Paid :", ": المدفوع"},
        {"Rest :", ": الباقي"},
        {"Client Name :", ": اسم العميل"},
        {"Dept :", ": الدين"},
        {"Email :", ": الايميل"},
        {"Name :", ": الاسم"},
        {"User :", ": المستخدم"},
        {"User Name :", ": اسم المستخدم"},
        {"Password :", ": الرقم السري"},
        {"Total Income :", ": المبيعات الكلية"},
        {"Date :", ": التاريخ"},
        {"US", "ديوننا"},
        {"Clients", "ديون العملاء"},
        {"Income", "الدخل"},
        {"Payments", "المدفوعات"},
        {"Out", "الصادر"},
        {"In", "الوارد"},
        {"Type :", ": النوع"},
        {"From :", ": من"},
        {"To :", ": إلي"},
        {"Alarm Range :", ": حد التنبيه"},
        {"Branch :", ": الفرع"},
        {"OK", "تم"},
        {"Hours :", ": الساعات"},
        {"SPrice :", ": س البيع"},
        {"PPrice :", ": س الشراء"},
        {"Add", "إضافة"},
        {"Notes :", ": ملاحظات"},
        {"Dept", "دين"},
        {"Payment", "مدفوعات"}
    };
    static boolean maximized = true;
    public static int Idflag = 0;

    public Home() {
        int count = 0;
        
        try {
            process = Runtime.getRuntime().exec(new String[]{"wmic", "bios", "get", "SerialNumber"});
            process.getOutputStream().close();
        } catch (IOException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }

        File directory = new File(filePath);
        try {
            if (!directory.exists()) {
                directory.mkdir();
            }
        } catch (Exception e) {
            filePath = "E:\\Hash";
            barcodeDest = "E:\\Hash\\Barcode";
            if (!directory.exists()) {
                directory.mkdir();
            }
        }

        DBcon d = new DBcon();
        try{
        d.rs = d.st.executeQuery("SELECT * FROM `cons` ;");
                while (d.rs.next()) {
                count = d.rs.getInt("quantity");
                }
                d.st = d.con.createStatement();
                    d.st.executeUpdate("UPDATE cons set quantity = '"+(count+1)+"';");
        }catch(Exception ex){
            
        }
        Scanner sc = new Scanner(process.getInputStream());
        String property = sc.next();
        String serial = sc.next();
        System.out.println(serial);
        if (serial.equals("HTJCX2J") && count <= 2) {
        initComponents();
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        pdf_lbl.setVisible(false);
        print_lbl.setVisible(false);
        add_branches.setVisible(false);
        delete_icon3.setVisible(false);
        del_lbl3.setVisible(false);
        edit_icon3.setVisible(false);
        edit_lbl3.setVisible(false);
        delete_icon4.setVisible(false);
        del_lbl4.setVisible(false);
        edit_icon4.setVisible(false);
        edit_lbl4.setVisible(false);
        user_lbl.setText(getUser());
        userLang();
        Typing();
        Start = Instant.now();
        //permission();
        styleTable(new JTable[]{Home0, Home1, Home2, Home3, Home4, Home5, clientT, consT, deptT, inventoryT, inventoryT1, salesT, jTable2, orderT, returnT, paymentT, userT});
        Date.setText(String.valueOf(LocalDate.now()));
        stockTable = createTableCols(jTable2, new String[]{"ID", "Item", "Quantity", "Price"});

        salesTable = createTableCols(salesT, new String[]{"ID", "Item", "Client Name", "Quantity", "Price"});
        clientTable = createTableCols(clientT, new String[]{"ID", "Name"});
        purchasesTable = createTableCols(paymentT, new String[]{"ID", "Item", "Client Name"});
        deptTable = createTableCols(deptT, new String[]{"ID", "Client Name", "Dept", "Paid"});
        consTable = createTableCols(consT, new String[]{"Name", "ID", "Quantity"});

        orderTable = createTableCols(orderT, new String[]{"ID", "Item", "Client Name", "Quantity"});
        inventoryTable1 = createTableCols(inventoryT, new String[]{"Item", "Client Name", "Quantity", "Total", "Date"});
        inventoryTable2 = createTableCols(inventoryT1, new String[]{"Item", "Client Name", "Quantity", "Total", "Date"});
        returningTable = createTableCols(returnT, new String[]{"ID", "Item", "Client Name", "Quantity"});

        userTable = createTableCols(userT, new String[]{"Name", "Password", "Type"});
        homeTable1 = createTableCols(Home0, new String[]{"Name", "Quantity", "Price"});
        homeTable2 = createTableCols(Home1, new String[]{"Item", "Client name", "Quantity"});
        homeTable3 = createTableCols(Home2, new String[]{"Item", "Client Name", "Quantity", "Price"});
        homeTable4 = createTableCols(Home3, new String[]{"Item", "Client Name", "Quantity", "Price"});
        homeTable5 = createTableCols(Home4, new String[]{"Item", "Client Name", "Quantity"});
        homeTable6 = createTableCols(Home5, new String[]{"Name", "Quantity"});
        homeTable7 = createTableCols(Home6, new String[]{"ID", "Item", "Total"});
        combobox();
        numbers();
        setColor(pane1, label1);
        resetColor(new JPanel[]{pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label2, label3, label4, label5, label6, label7, label8, label9, label14, label10, label11, label12, label13, label14});
        showPanel(homePanel);
        hidePanel(new JPanel[]{stockPanel, salesPanel, paymentPanel, deptPanel, ConsPanel, returnPanel, orderPanel, profitPanel, inventoryPanel, clientsPanel, usersPanel, helpPanel, aboutPanel});

        } else {
            JOptionPane.showMessageDialog(null, "You are not Allowed to run this program !!");
        }
    }
//
//    void timeElapsed() {
//        finish = Instant.now();
//        long timeElapsed = Duration.between(Start, finish).toHours();
//        int date = 0;
//        DBcon d = new DBcon();
//        String sql1 = "select date from cons";
//        try {
//            d.rs = d.st.executeQuery(sql1);
//            d.rs.last();
//            date = d.rs.getInt("date");
//        } catch (SQLException ex) {
//            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        String sql = "UPDATE cons SET date = " + (date - timeElapsed) + ";";
//        try {
//            d.st = d.con.createStatement();
//            d.st.executeUpdate(sql);
//        } catch (SQLException ex) {
//            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }

    void viewStock() {
        String name1 = "unknown", quant1 = null, id1 = null, price1 = null;
        if (stockTable.getRowCount() != 0) {
            for (int i = stockTable.getRowCount() - 1; i >= 0; i--) {
                stockTable.removeRow(i);
            }
        }
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `stock` where quantity != -1 and ID != -1 and ID != 0";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                name1 = d.rs.getString("Name");
                id1 = String.valueOf(d.rs.getLong("ID"));
                quant1 = String.valueOf(d.rs.getInt("Quantity"));
                price1 = String.valueOf(d.rs.getFloat("Price"));
                String[] rowData = {id1, name1, quant1, price1};
                stockTable.addRow(rowData);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }

    void viewSales() {
        if (salesTable.getRowCount() != 0) {
            for (int i = salesTable.getRowCount() - 1; i >= 0; i--) {
                salesTable.removeRow(i);
            }
        }
        String name1 = "unknown", quant1 = null, id1 = null, price2 = null;
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `sales` ,`clients`, stock , branch WHERE sales.stock_id= stock.ID AND  sales.client_id = `clients`.ID AND branch.id = stock.branch ";
        try {
            String item = null;
            d.rs = d.st.executeQuery(sql2);
            while (d.rs.next()) {
                id1 = String.valueOf(d.rs.getInt("sales.check_id"));
                quant1 = String.valueOf(d.rs.getInt("sales.quantity"));
                name1 = d.rs.getString("clients.Name");
                item = d.rs.getString("stock.Name");
                if (d.rs.getLong("stock.ID") != -1) {
                    price2 = String.valueOf(d.rs.getFloat("sales.Total") / d.rs.getInt("sales.quantity"));
                } else {
                    price2 = String.valueOf(d.rs.getFloat("sales.Total"));
                }
                String[] rowData = {id1, item, name1, quant1, price2};
                salesTable.addRow(rowData);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري" + ex);
        }
    }

    void viewPayment() {
        if (purchasesTable.getRowCount() != 0) {
            for (int i = purchasesTable.getRowCount() - 1; i >= 0; i--) {
                purchasesTable.removeRow(i);
            }
        }
        String name1 = "unknown", quant1 = null, id1 = null, price2 = null;
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `outgoings` ,`clients` WHERE  outgoings.client_id = `clients`.ID";
        try {
            String item = null;
            d.rs = d.st.executeQuery(sql2);
            while (d.rs.next()) {
                id1 = String.valueOf(d.rs.getInt("outgoings.check_id"));
                name1 = d.rs.getString("clients.Name");
                item = d.rs.getString("outgoings.stock_name");
                String[] rowData = {id1, item, name1};
                purchasesTable.addRow(rowData);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }

    void viewCons() {
        String name, id, quant;
        int cons;
        if (consTable.getRowCount() != 0) {
            for (int i = consTable.getRowCount() - 1; i >= 0; i--) {
                consTable.removeRow(i);
            }
        }

        DBcon d = new DBcon();

        String sql2 = "SELECT * FROM `stock` where quantity != -1";
        //sql2 = "SELECT * FROM `stock` WHERE Quantity < 50";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                cons = d.rs.getInt("cons");
                if (cons > d.rs.getInt("Quantity")) {
                    quant = String.valueOf(d.rs.getInt("Quantity"));
                    name = d.rs.getString("Name");
                    id = String.valueOf(d.rs.getLong("ID"));
                    String[] rowData = {name, id, quant};
                    consTable.addRow(rowData);
                }
            }
            //dm.getDataVector();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }

    void viewReturns() {
        if (returningTable.getRowCount() != 0) {
            for (int i = returningTable.getRowCount() - 1; i >= 0; i--) {
                returningTable.removeRow(i);
            }
        }
        String name1 = "unknown", quant1 = null, id1 = null, price = null;
        String sql3 = null;
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `returns` ,`clients`, stock WHERE returns.name = stock.ID AND  returns.client = `clients`.ID ";
        try {
            String item = null;
            d.rs = d.st.executeQuery(sql2);

            while (d.rs.next()) {
                id1 = String.valueOf(d.rs.getInt("returns.id"));
                quant1 = String.valueOf(d.rs.getInt("returns.quantity"));
                name1 = d.rs.getString("clients.Name");
                item = d.rs.getString("stock.Name");
                String[] rowData = {id1, item, name1, quant1};
                returningTable.addRow(rowData);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }

    void viewOrders() {
        if (orderTable.getRowCount() != 0) {
            for (int i = orderTable.getRowCount() - 1; i >= 0; i--) {
                orderTable.removeRow(i);
            }
        }
        String name1 = "unknown", id1 = null, quant1 = null, clientName = "unknown", Item = null;

        DBcon d = new DBcon();

        String sql2 = "SELECT * FROM `orders`";
        try {
            d.rs = d.st.executeQuery(sql2);
            while (d.rs.next()) {
                id1 = String.valueOf(d.rs.getInt("ID"));
                quant1 = String.valueOf(d.rs.getInt("Quantity"));
                clientName = d.rs.getString("Client_name");
                Item = d.rs.getString("Name");
                String[] rowData = {id1, Item, clientName, quant1};
                orderTable.addRow(rowData);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }

    void viewClients() {
        if (clientTable.getRowCount() != 0) {
            for (int i = clientTable.getRowCount() - 1; i >= 0; i--) {
                clientTable.removeRow(i);
            }
        }
        String name1 = "unknown", quant1 = null, id1 = null;
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `clients` WHERE ID != -1 AND ID != 0 AND ID != 1";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                name1 = d.rs.getString("Name");
                id1 = String.valueOf(d.rs.getInt("ID"));

                String[] rowData = {id1, name1};
                clientTable.addRow(rowData);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }

    void viewUsers() {
        if (userTable.getRowCount() != 0) {
            for (int i = userTable.getRowCount() - 1; i >= 0; i--) {
                userTable.removeRow(i);
            }
        }
        String name1, id1 = null, type = null;
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `users`";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                name1 = d.rs.getString("User_name");
                id1 = d.rs.getString("Password");
                type = d.rs.getString("Type");

                String[] rowData = {name1, id1, type};
                userTable.addRow(rowData);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }

    void TablePdf(DefaultTableModel table, String[] ColumnsName) {
        int rowCount = table.getRowCount();
        int columnCount = table.getColumnCount();
        String[][] rowData = new String[rowCount][columnCount];//= null ;
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                rowData[row][col] = String.valueOf(table.getValueAt(row, col));
            }
        }
        pdfi(ColumnsName, rowData);
    }

    void numbers() {
        DBcon d = new DBcon();
        int counter = 0;

        String sql2 = "SELECT * FROM `stock` where quantity != -1 and ID != -1 AND ID !=0";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                counter++;
            }
            jLabel1.setText(String.valueOf(counter));
            counter = 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
        sql2 = "SELECT * FROM `sales`";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                counter++;
            }
            jLabel7.setText(String.valueOf(counter));
            counter = 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
        sql2 = "SELECT * FROM `outgoings`";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                counter++;
            }
            jLabel8.setText(String.valueOf(counter));
            counter = 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
        sql2 = "SELECT * FROM `returns`";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                counter++;
            }
            jLabel12.setText(String.valueOf(counter));
            counter = 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }

        int cons = 0;
        sql2 = "SELECT * FROM `stock` WHERE quantity != -1";
        String quant;
        try {
            d.rs = d.st.executeQuery(sql2);

            while ((d.rs).next()) {
                quant = String.valueOf(d.rs.getInt("Quantity"));
                cons = d.rs.getInt("cons");
                if (cons > d.rs.getInt("Quantity")) {
                    counter++;
                }
            }
            jLabel14.setText(String.valueOf(counter));
            counter = 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
        sql2 = "SELECT * FROM `orders`";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                counter++;
            }
            jLabel6.setText(String.valueOf(counter));
            counter = 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }

    int getId(int id) {
        return id;
    }

    void Typing() {
        String name = user_lbl.getText();
        String type = null;
        int count = 0;
        String sql = "SELECT * FROM users WHERE User_name = '" + name + "';";
        DBcon d = new DBcon();
        try {
            d.rs = d.st.executeQuery(sql);
            while ((d.rs).next()) {
                count++;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
        if (count > 0) {
            try {
                d.rs = d.st.executeQuery(sql);
                while ((d.rs).next()) {
                    type = d.rs.getString("Type");
                }
                if (type.equals("User")) {
                    type_lbl1.setText("User");
                } else if (type.equals("Admin")) {
                    type_lbl1.setText("Admin");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
        } else {
            type_lbl1.setText("Super");
        }
    }

    int auto_insert(String s, String s2) {
        int x = 0;
        DBcon d = new DBcon();
        String sql = "SELECT * FROM `" + s + "`";
        try {
            d.rs = d.st.executeQuery(sql);
            while ((d.rs).next()) {

                if ((d.rs.getInt(s2)) == x) {
                    x++;
                }
            }
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري" );
        }
        return x;
    }

    void show(JScrollPane ok, JScrollPane no1, JScrollPane no2, JScrollPane no3, JScrollPane no4, JScrollPane no5) {
        ok.setVisible(true);
        no1.setVisible(false);
        no2.setVisible(false);
        no3.setVisible(false);
        no4.setVisible(false);
        no5.setVisible(false);
    }

    void branch(JComboBox j) {
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `branch`";
        j.removeAllItems();
        try {
            d.rs = d.st.executeQuery(sql2);
            while (d.rs.next()) {
                j.addItem(d.rs.getString("name"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");

        }
    }

    void combobox() {

        branch(salesComboBox);
        branch(stockComboBox);
        branch(consComboBox);
        branch(returnComboBox);
        branch(profitsComboBox1);
        branch(inventoryComboBox);
        branch(branch);

    }

    String getUser() {
        String j = null;
        int x = 0;
        DBcon d = new DBcon();
        String sql = "SELECT User_name FROM users WHERE logged = 1";
        try {
            d.rs = d.st.executeQuery(sql);
            while (d.rs.next()) {
                x++;
                //j = d.rs.getString("User_name");
            }
            if (x != 0) {
                d.rs = d.st.executeQuery(sql);
                while (d.rs.next()) {
                    j = d.rs.getString("User_name");
                }
            } else {
                j = "Hash";

            }
        } catch (SQLException ex) {
            Logger.getLogger(Home.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return j;
    }

    public DefaultTableModel delTblCol(JTable table, String[] cols) {
        DefaultTableModel dmodel;

        dmodel = (DefaultTableModel) table.getModel();
        for (int i = 0; i < cols.length; i++) {
            TableColumn col = table.getColumnModel().getColumn(i);
            table.removeColumn(col);
            table.revalidate();
        }
        return dmodel;
    }

    void pdfi(String s[], String s2[][]) {
        try {

            if (lang_lbl.getText().equals("عربي")) {
                String fileName = JOptionPane.showInputDialog("Enter File name : ");
                fileName += " " + Date.getText();
                createSamplePDF(fileName, s, s2);
                JOptionPane.showMessageDialog(null, "please wait the file will open now ");
                Desktop.getDesktop().open(new File(filePath + "\\PDF\\" + fileName + ".pdf"));
                //Desktop.getDesktop().open(new File("E:\\abdo" + fileName + ".pdf"));

            } else {
                String fileName = JOptionPane.showInputDialog("  : أدخل اسم الملف");
                fileName += " " + Date.getText();
                createSamplePDF(fileName, s, s2);
                JOptionPane.showMessageDialog(null, "برجاء الانتظار سيتم فتح الملف ");
                Desktop.getDesktop().open(new File(filePath + "\\PDF\\" + fileName + ".pdf"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري" + ex);
        }
    }

    private void showPanel(JPanel panel) {
        panel.setVisible(true);
    }

    DefaultTableModel createTableCols(JTable table, String[] cols) {
        DefaultTableModel dmodel;
        dmodel = (DefaultTableModel) table.getModel();
        for (int i = 0; i < cols.length; i++) {
            dmodel.addColumn(cols[i]);
        }
        return dmodel;
    }

    private void hidePanel(JPanel[] panel) {
        for (int i = 0; i < panel.length; i++) {
            panel[i].setVisible(false);
        }
    }

    private void setColor(JPanel pane, JLabel lbl) {
        pane.setBackground(new Color(44, 62, 80));
        lbl.setForeground(new Color(255, 255, 255));
    }

    private void resetColor(JPanel[] pane, JLabel[] lbl) {
        for (int i = 0; i < pane.length; i++) {
            pane[i].setBackground(new Color(255, 255, 255));
        }
        for (int i = 0; i < lbl.length; i++) {
            lbl[i].setForeground(new Color(0, 0, 0));
        }

    }

    private void setLabelIcon(JLabel lbl, String im) {
        lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource(im)));
    }

    public void convertLang(JLabel[] lbl, int index) {
        int sIndex;
        if (index == 0) {
            sIndex = 1;
        } else {
            sIndex = 0;
        }

        for (int i = 0; i < lbl.length; i++) {
            for (int j = 0; j < Names.length; j++) {
                if (lbl[i].getText().equals(Names[j][sIndex])) {
                    lbl[i].setText(Names[j][index]);
                    break;
                }

            }
        }
    }

    private void changeFontToArabic(JLabel[] lbl, String font) {
        for (int i = 0; i < lbl.length; i++) {
            lbl[i].setFont(new java.awt.Font(font, lbl[i].getFont().getStyle(), lbl[i].getFont().getSize() + 1));
        }
    }

    private void changeFontToEnglish(JLabel[] lbl, String font) {
        for (int i = 0; i < lbl.length; i++) {
            lbl[i].setFont(new java.awt.Font(font, lbl[i].getFont().getStyle(), lbl[i].getFont().getSize() - 1));
        }
    }

    private void showPlaceHolder(JLabel lbl, String txt) {
        lbl.setText(txt);
        lbl.setForeground(new java.awt.Color(204, 204, 255));
        lbl.setVisible(true);
    }

    private void hidePlaceHolder(JLabel lbl) {
        lbl.setVisible(false);
    }

    public static void styleTable(JTable[] tables) {

        for (int i = 0; i < tables.length; i++) {
            tables[i].getTableHeader().setPreferredSize(new Dimension(100, 50));
            tables[i].getTableHeader().setFont(new java.awt.Font("Segoe UI", 1, 14));
            tables[i].setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        }

    }

//    private void PlaceHolders() {
//        showPlaceHolder(id_place2, "Ex : 100");
//        showPlaceHolder(name_place2, "Ex : Mercedez benz");
//        showPlaceHolder(quan_place2, "Ex : 1000");
//        showPlaceHolder(price_place2, "Ex : 1000000");
//    }
    public static void createSamplePDF(String fileName, String header[], String body[][]) throws Exception {
        Document documento = new Document();

        File directory = new File(filePath + "\\PDF");
        if (!directory.exists()) {
            directory.mkdir();
        }

        File file = new File(filePath + "\\PDF\\" + fileName + ".pdf");

        file.createNewFile();
        FileOutputStream fop = new FileOutputStream(file);
        PdfWriter.getInstance(documento, fop);
        documento.open();
        //Fonts
        Font fontHeader = FontFactory.getFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, 18);
        Font fontBody = FontFactory.getFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, 14);

        //Table for header
        PdfPTable cabetabla = new PdfPTable(header.length);
        // handling arabic header
        cabetabla.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

        for (int j = 0; j < header.length; j++) {
            Phrase frase = new Phrase(header[j], fontHeader);
            PdfPCell cell = new PdfPCell(frase);
            cell.setBackgroundColor(BaseColor.ORANGE);
            cabetabla.addCell(cell);
        }
        documento.add(cabetabla);
        //Tabla for body
        PdfPTable table = new PdfPTable(header.length);
        //handling arabic body
        table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        for (int i = 0; i < body.length; i++) {
            for (int j = 0; j < body[i].length; j++) {
                table.addCell(new Phrase(body[i][j], fontBody));

            }
        }
        documento.add(table);
        documento.close();
        fop.flush();
        fop.close();
    }

    private void tableCenter(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public String formatNumber(String S) {
        if (S.length() < 5) {
            S = "000" + S;
        } else if (S.length() < 6) {
            S = "00" + S;
        } else if (S.length() < 7) {
            S = "0" + S;
        }

        return S;
    }

    public void createBarcodePdf(int start, int end) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(barcodeDest + "\\barcode.pdf"));
        document.open();
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);

        for (int i = start; i <= end; i++) {
            table.addCell(createBarcode(writer, String.format("%09d", i)));
        }
        document.add(table);
        document.close();
    }

    public static PdfPCell createBarcode(PdfWriter writer, String code) throws DocumentException, IOException {

        Barcode128 barcode = new Barcode128();
        barcode.setGenerateChecksum(true);
        barcode.setCode(code);

        //BarcodeEAN barcode = new BarcodeEAN();
        //  barcode.setCodeType(Barcode.EAN8);
        // barcode.setCode(code);
        PdfPCell cell = new PdfPCell(barcode.createImageWithBarcode(writer.getDirectContent(), BaseColor.BLACK, BaseColor.GRAY), true);
        cell.setPadding(10);
        cell.setBorderColor(BaseColor.WHITE);
        return cell;
    }

    void userLang() {
        int langFlag = 0;
        for (int i = 0; i < user_lbl.getText().length(); i++) {
            if (((int) user_lbl.getText().charAt(i) >= 97 && (int) user_lbl.getText().charAt(i) <= 122) || ((int) user_lbl.getText().charAt(i) >= 65 && (int) user_lbl.getText().charAt(i) <= 99)) {
                continue;
            }
            langFlag = 1;
        }

        if (langFlag == 0) {
            userNameLang = "E";
        } else {
            userNameLang = "A";
        }

    }

    // start abdo
    // end Abdo
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        entityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("stocks?zeroDateTimeBehavior=convertToNullPU").createEntityManager();
        stockQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT s FROM Stock s");
        stockList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : stockQuery.getResultList();
        stockQuery1 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT s FROM Stock s");
        stockList1 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : stockQuery1.getResultList();
        ordersQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT o FROM Orders o");
        ordersList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : ordersQuery.getResultList();
        salesQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT s FROM Sales s");
        salesList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : salesQuery.getResultList();
        purchasesQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT p FROM Purchases p");
        purchasesList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : purchasesQuery.getResultList();
        consQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT c FROM Cons c");
        consList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : consQuery.getResultList();
        stockQuery2 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT s FROM Stock s");
        stockList2 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : stockQuery2.getResultList();
        stockQuery3 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT s FROM Stock s");
        stockList3 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : stockQuery3.getResultList();
        stockQuery4 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT s FROM Stock s");
        stockList4 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : stockQuery4.getResultList();
        stockQuery5 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT s FROM Stock s");
        stockList5 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : stockQuery5.getResultList();
        stockQuery6 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT s FROM Stock s");
        stockList6 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : stockQuery6.getResultList();
        returnsQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT r FROM Returns r");
        returnsList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : returnsQuery.getResultList();
        stockQuery7 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT s FROM Stock s");
        stockList7 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : stockQuery7.getResultList();
        salesQuery1 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT s FROM Sales s");
        salesList1 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : salesQuery1.getResultList();
        stockQuery8 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT s FROM Stock s");
        stockList8 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : stockQuery8.getResultList();
        stockQuery9 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT s FROM Stock s");
        stockList9 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : stockQuery9.getResultList();
        stockQuery10 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT s FROM Stock s");
        stockList10 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : stockQuery10.getResultList();
        fullPanel = new javax.swing.JPanel();
        sidePanel = new javax.swing.JPanel();
        pane1 = new javax.swing.JPanel();
        label1 = new javax.swing.JLabel();
        pane2 = new javax.swing.JPanel();
        label2 = new javax.swing.JLabel();
        pane3 = new javax.swing.JPanel();
        label3 = new javax.swing.JLabel();
        pane4 = new javax.swing.JPanel();
        label4 = new javax.swing.JLabel();
        pane5 = new javax.swing.JPanel();
        label5 = new javax.swing.JLabel();
        pane6 = new javax.swing.JPanel();
        label6 = new javax.swing.JLabel();
        pane7 = new javax.swing.JPanel();
        label7 = new javax.swing.JLabel();
        pane8 = new javax.swing.JPanel();
        label8 = new javax.swing.JLabel();
        pane9 = new javax.swing.JPanel();
        label9 = new javax.swing.JLabel();
        pane10 = new javax.swing.JPanel();
        label10 = new javax.swing.JLabel();
        pane11 = new javax.swing.JPanel();
        label11 = new javax.swing.JLabel();
        pane12 = new javax.swing.JPanel();
        label12 = new javax.swing.JLabel();
        pane13 = new javax.swing.JPanel();
        label13 = new javax.swing.JLabel();
        pane14 = new javax.swing.JPanel();
        label14 = new javax.swing.JLabel();
        sideBorderPanel = new javax.swing.JPanel();
        titlePanel = new javax.swing.JPanel();
        user_lbl = new javax.swing.JLabel();
        type_lbl1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        pdf_lbl = new javax.swing.JLabel();
        lang_lbl = new javax.swing.JLabel();
        Date = new javax.swing.JLabel();
        print_lbl = new javax.swing.JLabel();
        barcode_lbl = new javax.swing.JLabel();
        homePanel = new javax.swing.JPanel();
        homePanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        stock_lbl1 = new javax.swing.JLabel();
        orders_lbl1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        sales_lbl1 = new javax.swing.JLabel();
        purchase_lbl1 = new javax.swing.JLabel();
        returns_lbl1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cons_lbl1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        eyeLabel5 = new javax.swing.JLabel();
        eyeLabel1 = new javax.swing.JLabel();
        eyeLabel2 = new javax.swing.JLabel();
        eyeLabel3 = new javax.swing.JLabel();
        eyeLabel4 = new javax.swing.JLabel();
        eyeLabel6 = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        start = new javax.swing.JLabel();
        end = new javax.swing.JLabel();
        shiftShow = new javax.swing.JLabel();
        dayShow = new javax.swing.JLabel();
        start1 = new javax.swing.JLabel();
        total2 = new javax.swing.JLabel();
        end1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        homePanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Home0 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        Home1 = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        Home2 = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        Home3 = new javax.swing.JTable();
        jScrollPane11 = new javax.swing.JScrollPane();
        Home4 = new javax.swing.JTable();
        jScrollPane12 = new javax.swing.JScrollPane();
        Home5 = new javax.swing.JTable();
        jScrollPane20 = new javax.swing.JScrollPane();
        Home6 = new javax.swing.JTable();
        stockPanel = new javax.swing.JPanel();
        stockPanel1 = new javax.swing.JPanel();
        itemPanel2 = new javax.swing.JPanel();
        id_lbl2 = new javax.swing.JLabel();
        quan_lbl2 = new javax.swing.JLabel();
        price_lbl2 = new javax.swing.JLabel();
        stockpurchasesPrice = new javax.swing.JTextField();
        name_lbl2 = new javax.swing.JLabel();
        stock_name = new javax.swing.JTextField();
        stock_id = new javax.swing.JTextField();
        stock_quan = new javax.swing.JTextField();
        branch_lbl2 = new javax.swing.JLabel();
        alarmRange = new javax.swing.JTextField();
        price_lbl6 = new javax.swing.JLabel();
        stocksalePrice = new javax.swing.JTextField();
        alarm_lbl2 = new javax.swing.JLabel();
        stockComboBox = new javax.swing.JComboBox<>();
        view_icon2 = new javax.swing.JLabel();
        view_lbl2 = new javax.swing.JLabel();
        add_icon2 = new javax.swing.JLabel();
        add_lbl2 = new javax.swing.JLabel();
        delete_icon2 = new javax.swing.JLabel();
        del_lbl2 = new javax.swing.JLabel();
        edit_icon2 = new javax.swing.JLabel();
        edit_lbl2 = new javax.swing.JLabel();
        clear_icon2 = new javax.swing.JLabel();
        search_lbl2 = new javax.swing.JLabel();
        stockPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        salesPanel = new javax.swing.JPanel();
        salesPanel1 = new javax.swing.JPanel();
        itemPanel3 = new javax.swing.JPanel();
        ch_lbl3 = new javax.swing.JLabel();
        salesCheckId = new javax.swing.JTextField();
        salesStockName = new javax.swing.JTextField();
        quan_lbl3 = new javax.swing.JLabel();
        salesQuant = new javax.swing.JTextField();
        price_lbl3 = new javax.swing.JLabel();
        salesClient = new javax.swing.JTextField();
        cName_lbl3 = new javax.swing.JLabel();
        salesPrice = new javax.swing.JTextField();
        name_lbl3 = new javax.swing.JLabel();
        salestotal = new javax.swing.JTextField();
        total_lbl3 = new javax.swing.JLabel();
        salespaid = new javax.swing.JTextField();
        paid_lbl3 = new javax.swing.JLabel();
        salesRest = new javax.swing.JTextField();
        rest_lbl3 = new javax.swing.JLabel();
        salesMob = new javax.swing.JTextField();
        mob_lbl3 = new javax.swing.JLabel();
        userSales = new javax.swing.JTextField();
        mob_lbl6 = new javax.swing.JLabel();
        salesDate = new javax.swing.JTextField();
        rest_lbl5 = new javax.swing.JLabel();
        branch_lbl3 = new javax.swing.JLabel();
        salesComboBox = new javax.swing.JComboBox<>();
        multiple_sales_lbl = new javax.swing.JLabel();
        salesStockId = new javax.swing.JTextField();
        item_lbl3 = new javax.swing.JLabel();
        deptBox1 = new javax.swing.JCheckBox();
        dept_lbl00 = new javax.swing.JLabel();
        view_icon3 = new javax.swing.JLabel();
        view_lbl3 = new javax.swing.JLabel();
        add_icon3 = new javax.swing.JLabel();
        add_lbl3 = new javax.swing.JLabel();
        delete_icon3 = new javax.swing.JLabel();
        del_lbl3 = new javax.swing.JLabel();
        edit_icon3 = new javax.swing.JLabel();
        edit_lbl3 = new javax.swing.JLabel();
        clear_icon3 = new javax.swing.JLabel();
        search_lbl3 = new javax.swing.JLabel();
        salesPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        salesT = new javax.swing.JTable();
        paymentPanel = new javax.swing.JPanel();
        paymentPanel1 = new javax.swing.JPanel();
        itemPanel4 = new javax.swing.JPanel();
        ch_lbl4 = new javax.swing.JLabel();
        totalPayment = new javax.swing.JTextField();
        rest_lbl4 = new javax.swing.JLabel();
        namePayment = new javax.swing.JTextField();
        checkIdPayment = new javax.swing.JTextField();
        paid_lbl4 = new javax.swing.JLabel();
        notes_lbl = new javax.swing.JLabel();
        restPayment = new javax.swing.JTextField();
        clientNamePayment = new javax.swing.JTextField();
        paidPayment = new javax.swing.JTextField();
        cName_lbl4 = new javax.swing.JLabel();
        name_lbl4 = new javax.swing.JLabel();
        mobilePayment = new javax.swing.JTextField();
        mob_lbl4 = new javax.swing.JLabel();
        UserPayment = new javax.swing.JTextField();
        mob_lbl5 = new javax.swing.JLabel();
        paymentDate = new javax.swing.JTextField();
        date_lbl4 = new javax.swing.JLabel();
        paymentBox1 = new javax.swing.JCheckBox();
        deptBox2 = new javax.swing.JCheckBox();
        jScrollPane19 = new javax.swing.JScrollPane();
        notes = new javax.swing.JTextArea();
        total_lbl9 = new javax.swing.JLabel();
        payment_lbl = new javax.swing.JLabel();
        dept_lbl01 = new javax.swing.JLabel();
        view_icon4 = new javax.swing.JLabel();
        view_lbl4 = new javax.swing.JLabel();
        add_icon4 = new javax.swing.JLabel();
        add_lbl4 = new javax.swing.JLabel();
        delete_icon4 = new javax.swing.JLabel();
        del_lbl4 = new javax.swing.JLabel();
        edit_icon4 = new javax.swing.JLabel();
        edit_lbl4 = new javax.swing.JLabel();
        clear_icon4 = new javax.swing.JLabel();
        search_lbl4 = new javax.swing.JLabel();
        paymentPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        paymentT = new javax.swing.JTable();
        deptPanel = new javax.swing.JPanel();
        deptPanel1 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        myDeptL = new javax.swing.JLabel();
        dept = new javax.swing.JLabel();
        myDeptE = new javax.swing.JLabel();
        deptL = new javax.swing.JLabel();
        deptL1 = new javax.swing.JLabel();
        deptL2 = new javax.swing.JLabel();
        deptPanel2 = new javax.swing.JPanel();
        itemPanel5 = new javax.swing.JPanel();
        ch_lbl5 = new javax.swing.JLabel();
        Total = new javax.swing.JTextField();
        Name = new javax.swing.JTextField();
        check_id = new javax.swing.JTextField();
        total_lbl5 = new javax.swing.JLabel();
        client_name = new javax.swing.JTextField();
        cName_lbl5 = new javax.swing.JLabel();
        name_lbl5 = new javax.swing.JLabel();
        deptPaid = new javax.swing.JTextField();
        total_lbl6 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        deptT = new javax.swing.JTable();
        ConsPanel = new javax.swing.JPanel();
        ConsPanel1 = new javax.swing.JPanel();
        itemPanel6 = new javax.swing.JPanel();
        id_lbl6 = new javax.swing.JLabel();
        quan_lbl6 = new javax.swing.JLabel();
        consId = new javax.swing.JTextField();
        name_lbl6 = new javax.swing.JLabel();
        consName = new javax.swing.JTextField();
        consQuantity = new javax.swing.JTextField();
        branch_lbl6 = new javax.swing.JLabel();
        consComboBox = new javax.swing.JComboBox<>();
        view_icon6 = new javax.swing.JLabel();
        view_lbl6 = new javax.swing.JLabel();
        clear_icon6 = new javax.swing.JLabel();
        search_lbl6 = new javax.swing.JLabel();
        ConsPanel2 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        consT = new javax.swing.JTable();
        returnPanel = new javax.swing.JPanel();
        returnPanel1 = new javax.swing.JPanel();
        itemPanel7 = new javax.swing.JPanel();
        id_lbl7 = new javax.swing.JLabel();
        returnId = new javax.swing.JTextField();
        returnQuantity = new javax.swing.JTextField();
        quan_lbl7 = new javax.swing.JLabel();
        returnPrice = new javax.swing.JTextField();
        price_lbl7 = new javax.swing.JLabel();
        returnStockName = new javax.swing.JTextField();
        name_lbl7 = new javax.swing.JLabel();
        returnClient = new javax.swing.JTextField();
        cName_lbl7 = new javax.swing.JLabel();
        returnTotal = new javax.swing.JTextField();
        total_lbl7 = new javax.swing.JLabel();
        returnPaid = new javax.swing.JTextField();
        paid_lbl7 = new javax.swing.JLabel();
        returnRest = new javax.swing.JTextField();
        rest_lbl7 = new javax.swing.JLabel();
        returnClient1 = new javax.swing.JTextField();
        cName_lbl9 = new javax.swing.JLabel();
        returnsDate = new javax.swing.JTextField();
        date_lbl7 = new javax.swing.JLabel();
        returnComboBox = new javax.swing.JComboBox<>();
        branch_lbl7 = new javax.swing.JLabel();
        deptBox3 = new javax.swing.JCheckBox();
        multiple_return_lbl = new javax.swing.JLabel();
        returnStrockId = new javax.swing.JTextField();
        id2_lbl9 = new javax.swing.JLabel();
        return_dept_lbl = new javax.swing.JLabel();
        view_icon7 = new javax.swing.JLabel();
        view_lbl7 = new javax.swing.JLabel();
        add_icon7 = new javax.swing.JLabel();
        add_lbl7 = new javax.swing.JLabel();
        clear_icon7 = new javax.swing.JLabel();
        search_lbl7 = new javax.swing.JLabel();
        returnPanel2 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        returnT = new javax.swing.JTable();
        orderPanel = new javax.swing.JPanel();
        orderPanel1 = new javax.swing.JPanel();
        itemPanel8 = new javax.swing.JPanel();
        id_lbl8 = new javax.swing.JLabel();
        orderId = new javax.swing.JTextField();
        orderQuantity = new javax.swing.JTextField();
        quan_lbl8 = new javax.swing.JLabel();
        orderPrice = new javax.swing.JTextField();
        price_lbl8 = new javax.swing.JLabel();
        orderName = new javax.swing.JTextField();
        name_lbl8 = new javax.swing.JLabel();
        orderClient = new javax.swing.JTextField();
        cName_lbl8 = new javax.swing.JLabel();
        orderTotal = new javax.swing.JTextField();
        total_lbl8 = new javax.swing.JLabel();
        orderPaid = new javax.swing.JTextField();
        paid_lbl8 = new javax.swing.JLabel();
        orderRest = new javax.swing.JTextField();
        rest_lbl8 = new javax.swing.JLabel();
        orderClient1 = new javax.swing.JTextField();
        cName_lbl10 = new javax.swing.JLabel();
        ordersDate3 = new javax.swing.JTextField();
        rest_lbl10 = new javax.swing.JLabel();
        deptBox4 = new javax.swing.JCheckBox();
        order_dept_lbl = new javax.swing.JLabel();
        view_icon8 = new javax.swing.JLabel();
        view_lbl8 = new javax.swing.JLabel();
        add_icon8 = new javax.swing.JLabel();
        add_lbl8 = new javax.swing.JLabel();
        delete_icon8 = new javax.swing.JLabel();
        del_lbl8 = new javax.swing.JLabel();
        edit_icon8 = new javax.swing.JLabel();
        edit_lbl8 = new javax.swing.JLabel();
        clear_icon8 = new javax.swing.JLabel();
        search_lbl8 = new javax.swing.JLabel();
        orderPanel2 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        orderT = new javax.swing.JTable();
        profitPanel = new javax.swing.JPanel();
        decideImgLbl = new javax.swing.JLabel();
        profitPanel1 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        yearLbl1 = new javax.swing.JLabel();
        dayLbl1 = new javax.swing.JLabel();
        monthLbl1 = new javax.swing.JLabel();
        inLabel = new javax.swing.JLabel();
        inLabel4 = new javax.swing.JLabel();
        from_lbl1 = new javax.swing.JLabel();
        dateChooserCombo4 = new datechooser.beans.DateChooserCombo();
        branch_lbl9 = new javax.swing.JLabel();
        profitsComboBox1 = new javax.swing.JComboBox<>();
        profitPanel2 = new javax.swing.JPanel();
        outLabel = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        yearLbl2 = new javax.swing.JLabel();
        monthLbl2 = new javax.swing.JLabel();
        dayLbl2 = new javax.swing.JLabel();
        inLabel7 = new javax.swing.JLabel();
        profitPanel3 = new javax.swing.JPanel();
        outLabel1 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        yearLbl4 = new javax.swing.JLabel();
        monthLbl4 = new javax.swing.JLabel();
        dayLbl4 = new javax.swing.JLabel();
        inLabel8 = new javax.swing.JLabel();
        to_lbl1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        dateChooserCombo3 = new datechooser.beans.DateChooserCombo();
        ok_lbl1 = new javax.swing.JLabel();
        inventoryPanel = new javax.swing.JPanel();
        inventoryPanel1 = new javax.swing.JPanel();
        dayLbl3 = new javax.swing.JLabel();
        monthLbl3 = new javax.swing.JLabel();
        yearLbl3 = new javax.swing.JLabel();
        inLabel6 = new javax.swing.JLabel();
        inLabel1 = new javax.swing.JLabel();
        inLabel2 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        inLabel5 = new javax.swing.JLabel();
        return_inventory = new javax.swing.JLabel();
        return_lbl = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        inventoryComboBox = new javax.swing.JComboBox<>();
        branch_lbl10 = new javax.swing.JLabel();
        ok_lbl2 = new javax.swing.JLabel();
        dateChooserCombo5 = new datechooser.beans.DateChooserCombo();
        dateChooserCombo6 = new datechooser.beans.DateChooserCombo();
        from_lbl2 = new javax.swing.JLabel();
        to_lbl2 = new javax.swing.JLabel();
        inventoryPanel2 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        inventoryT = new javax.swing.JTable();
        jScrollPane17 = new javax.swing.JScrollPane();
        inventoryT1 = new javax.swing.JTable();
        outLabel9 = new javax.swing.JLabel();
        inLabel9 = new javax.swing.JLabel();
        clientsPanel = new javax.swing.JPanel();
        clientsPanel1 = new javax.swing.JPanel();
        itemPanel10 = new javax.swing.JPanel();
        id_lbl11 = new javax.swing.JLabel();
        jTextField61 = new javax.swing.JTextField();
        jTextField64 = new javax.swing.JTextField();
        name_lbl11 = new javax.swing.JLabel();
        dept_lbl11 = new javax.swing.JLabel();
        jTextField70 = new javax.swing.JTextField();
        jTextField83 = new javax.swing.JTextField();
        mob_lbl11 = new javax.swing.JLabel();
        view_icon11 = new javax.swing.JLabel();
        view_lbl11 = new javax.swing.JLabel();
        add_icon11 = new javax.swing.JLabel();
        add_lbl11 = new javax.swing.JLabel();
        delete_icon11 = new javax.swing.JLabel();
        del_lbl11 = new javax.swing.JLabel();
        edit_icon11 = new javax.swing.JLabel();
        edit_lbl11 = new javax.swing.JLabel();
        clear_icon11 = new javax.swing.JLabel();
        search_lbl11 = new javax.swing.JLabel();
        clientsPanel2 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        clientT = new javax.swing.JTable();
        usersPanel = new javax.swing.JPanel();
        usersPanel4 = new javax.swing.JPanel();
        itemPanel11 = new javax.swing.JPanel();
        password = new javax.swing.JTextField();
        pass_lbl12 = new javax.swing.JLabel();
        userName = new javax.swing.JTextField();
        user_lbl12 = new javax.swing.JLabel();
        type_lbl12 = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        programHours = new javax.swing.JTextField();
        hours_lbl = new javax.swing.JLabel();
        add_hours = new javax.swing.JLabel();
        type = new javax.swing.JComboBox<>();
        user7 = new javax.swing.JCheckBox();
        user8 = new javax.swing.JCheckBox();
        user9 = new javax.swing.JCheckBox();
        user10 = new javax.swing.JCheckBox();
        user3 = new javax.swing.JCheckBox();
        user4 = new javax.swing.JCheckBox();
        user5 = new javax.swing.JCheckBox();
        user6 = new javax.swing.JCheckBox();
        user2 = new javax.swing.JCheckBox();
        user1 = new javax.swing.JCheckBox();
        check_lbl1 = new javax.swing.JLabel();
        check_lbl2 = new javax.swing.JLabel();
        check_lbl3 = new javax.swing.JLabel();
        check_lbl4 = new javax.swing.JLabel();
        check_lbl5 = new javax.swing.JLabel();
        check_lbl6 = new javax.swing.JLabel();
        check_lbl7 = new javax.swing.JLabel();
        check_lbl8 = new javax.swing.JLabel();
        check_lbl9 = new javax.swing.JLabel();
        check_lbl10 = new javax.swing.JLabel();
        branch = new javax.swing.JComboBox<>();
        branch_lbl12 = new javax.swing.JLabel();
        add_branches = new javax.swing.JLabel();
        view_icon12 = new javax.swing.JLabel();
        view_lbl12 = new javax.swing.JLabel();
        add_icon12 = new javax.swing.JLabel();
        delete_icon12 = new javax.swing.JLabel();
        del_lbl12 = new javax.swing.JLabel();
        edit_icon12 = new javax.swing.JLabel();
        edit_lbl12 = new javax.swing.JLabel();
        clear_icon12 = new javax.swing.JLabel();
        search_lbl12 = new javax.swing.JLabel();
        add_lbl13 = new javax.swing.JLabel();
        usersPanel5 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        userT = new javax.swing.JTable();
        helpPanel = new javax.swing.JPanel();
        helpPanel1 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        videoPlay1 = new javax.swing.JLabel();
        helpPanel2 = new javax.swing.JPanel();
        aboutPanel = new javax.swing.JPanel();
        aboutPanel1 = new javax.swing.JPanel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(0, 20));
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(1370, 790));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fullPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sidePanel.setBackground(new java.awt.Color(255, 255, 255));
        sidePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pane1.setBackground(new java.awt.Color(44, 62, 80));
        pane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane1MousePressed(evt);
            }
        });
        pane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label1.setText("Home");
        pane1.add(label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        sidePanel.add(pane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 41));

        pane2.setBackground(new java.awt.Color(255, 255, 255));
        pane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane2MousePressed(evt);
            }
        });
        pane2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label2.setText("Stock");
        pane2.add(label2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        sidePanel.add(pane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 160, 41));

        pane3.setBackground(new java.awt.Color(255, 255, 255));
        pane3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane3MousePressed(evt);
            }
        });
        pane3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label3.setText("Sales");
        pane3.add(label3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        sidePanel.add(pane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 160, 41));

        pane4.setBackground(new java.awt.Color(255, 255, 255));
        pane4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane4MousePressed(evt);
            }
        });
        pane4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label4.setText("Payments");
        pane4.add(label4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        sidePanel.add(pane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 160, 41));

        pane5.setBackground(new java.awt.Color(255, 255, 255));
        pane5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane5MousePressed(evt);
            }
        });
        pane5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label5.setText("Depts");
        pane5.add(label5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        sidePanel.add(pane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 160, 41));

        pane6.setBackground(new java.awt.Color(255, 255, 255));
        pane6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane6MousePressed(evt);
            }
        });
        pane6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label6.setText("Cons");
        pane6.add(label6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        sidePanel.add(pane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 160, 41));

        pane7.setBackground(new java.awt.Color(255, 255, 255));
        pane7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane7MousePressed(evt);
            }
        });
        pane7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label7.setText("Returns");
        pane7.add(label7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        sidePanel.add(pane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 160, 41));

        pane8.setBackground(new java.awt.Color(255, 255, 255));
        pane8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane8MousePressed(evt);
            }
        });
        pane8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label8.setText("Orders");
        pane8.add(label8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        sidePanel.add(pane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 160, 41));

        pane9.setBackground(new java.awt.Color(255, 255, 255));
        pane9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane9MousePressed(evt);
            }
        });
        pane9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label9.setText("Profits");
        pane9.add(label9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        sidePanel.add(pane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 160, 41));

        pane10.setBackground(new java.awt.Color(255, 255, 255));
        pane10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane10MousePressed(evt);
            }
        });
        pane10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label10.setText("Inventory");
        pane10.add(label10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        sidePanel.add(pane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 160, 41));

        pane11.setBackground(new java.awt.Color(255, 255, 255));
        pane11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane11MousePressed(evt);
            }
        });
        pane11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label11.setText("Clients");
        pane11.add(label11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        sidePanel.add(pane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 160, 41));

        pane12.setBackground(new java.awt.Color(255, 255, 255));
        pane12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane12MousePressed(evt);
            }
        });
        pane12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("Users");
        pane12.add(label12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        sidePanel.add(pane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 160, 41));

        pane13.setBackground(new java.awt.Color(255, 255, 255));
        pane13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane13MousePressed(evt);
            }
        });
        pane13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label13.setText("Help");
        label13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label13MouseClicked(evt);
            }
        });
        pane13.add(label13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        sidePanel.add(pane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 160, 41));

        pane14.setBackground(new java.awt.Color(255, 255, 255));
        pane14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane14MousePressed(evt);
            }
        });
        pane14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label14.setText("About");
        pane14.add(label14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        sidePanel.add(pane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 520, 160, 41));

        fullPanel.add(sidePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 160, 680));

        sideBorderPanel.setBackground(new java.awt.Color(189, 195, 199));

        javax.swing.GroupLayout sideBorderPanelLayout = new javax.swing.GroupLayout(sideBorderPanel);
        sideBorderPanel.setLayout(sideBorderPanelLayout);
        sideBorderPanelLayout.setHorizontalGroup(
            sideBorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        sideBorderPanelLayout.setVerticalGroup(
            sideBorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 785, Short.MAX_VALUE)
        );

        fullPanel.add(sideBorderPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1237, 0, 3, 785));

        titlePanel.setBackground(new java.awt.Color(53, 59, 72));
        titlePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        user_lbl.setFont(new java.awt.Font("Segoe UI", 0, 52)); // NOI18N
        user_lbl.setForeground(new java.awt.Color(255, 255, 255));
        user_lbl.setText("Ahmed Hashem");
        titlePanel.add(user_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, -1, -1));

        type_lbl1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        type_lbl1.setForeground(new java.awt.Color(153, 153, 153));
        type_lbl1.setText("Owner");
        titlePanel.add(type_lbl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close.png"))); // NOI18N
        jLabel4.setText("X");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel4MousePressed(evt);
            }
        });
        titlePanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 31, 30, 28));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mlogo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel17)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
        );

        titlePanel.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 130));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/max.png"))); // NOI18N
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel18MousePressed(evt);
            }
        });
        titlePanel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(1145, 30, -1, 30));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lock.png"))); // NOI18N
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel19MousePressed(evt);
            }
        });
        titlePanel.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(1097, 32, -1, -1));

        pdf_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pdf.png"))); // NOI18N
        pdf_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pdf_lblMousePressed(evt);
            }
        });
        titlePanel.add(pdf_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 20, -1, -1));

        lang_lbl.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lang_lbl.setForeground(new java.awt.Color(255, 255, 255));
        lang_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lang_lbl.setText("عربي");
        lang_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lang_lblMousePressed(evt);
            }
        });
        titlePanel.add(lang_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 180, 60));

        Date.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        Date.setForeground(new java.awt.Color(255, 255, 255));
        Date.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titlePanel.add(Date, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 60, 150, 50));

        print_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print.png"))); // NOI18N
        print_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                print_lblMouseClicked(evt);
            }
        });
        titlePanel.add(print_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 20, -1, -1));

        barcode_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/barcode.png"))); // NOI18N
        barcode_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                barcode_lblMouseClicked(evt);
            }
        });
        titlePanel.add(barcode_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 20, 60, -1));

        fullPanel.add(titlePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1240, 110));

        homePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homePanel1.setBackground(new java.awt.Color(113, 128, 147));
        homePanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("0000000");
        homePanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 160, -1));

        stock_lbl1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        stock_lbl1.setForeground(new java.awt.Color(44, 62, 80));
        stock_lbl1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stock_lbl1.setText("Stock");
        homePanel1.add(stock_lbl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 140, -1));

        orders_lbl1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        orders_lbl1.setForeground(new java.awt.Color(44, 62, 80));
        orders_lbl1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        orders_lbl1.setText("Orders");
        homePanel1.add(orders_lbl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 210, 140, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("0000000");
        homePanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 270, 160, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("0000000");
        homePanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 150, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("0000000");
        homePanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 160, -1));

        sales_lbl1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        sales_lbl1.setForeground(new java.awt.Color(44, 62, 80));
        sales_lbl1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sales_lbl1.setText("Sales");
        homePanel1.add(sales_lbl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 140, -1));

        purchase_lbl1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        purchase_lbl1.setForeground(new java.awt.Color(44, 62, 80));
        purchase_lbl1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        purchase_lbl1.setText("Payments");
        homePanel1.add(purchase_lbl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 200, -1));

        returns_lbl1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        returns_lbl1.setForeground(new java.awt.Color(44, 62, 80));
        returns_lbl1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        returns_lbl1.setText("Returns");
        homePanel1.add(returns_lbl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 180, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("0000000");
        homePanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 160, -1));

        cons_lbl1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        cons_lbl1.setForeground(new java.awt.Color(44, 62, 80));
        cons_lbl1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cons_lbl1.setText("Cons");
        homePanel1.add(cons_lbl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, 170, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("0000000");
        homePanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, 150, -1));

        eyeLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye2.png"))); // NOI18N
        eyeLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eyeLabel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                eyeLabel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                eyeLabel5MouseExited(evt);
            }
        });
        homePanel1.add(eyeLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 320, -1, -1));

        eyeLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye2.png"))); // NOI18N
        eyeLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eyeLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                eyeLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                eyeLabel1MouseExited(evt);
            }
        });
        homePanel1.add(eyeLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 120, -1, -1));

        eyeLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye2.png"))); // NOI18N
        eyeLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eyeLabel2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                eyeLabel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                eyeLabel2MouseExited(evt);
            }
        });
        homePanel1.add(eyeLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 320, -1, -1));

        eyeLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye2.png"))); // NOI18N
        eyeLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eyeLabel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                eyeLabel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                eyeLabel3MouseExited(evt);
            }
        });
        homePanel1.add(eyeLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(238, 120, 50, -1));

        eyeLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye2.png"))); // NOI18N
        eyeLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eyeLabel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                eyeLabel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                eyeLabel4MouseExited(evt);
            }
        });
        homePanel1.add(eyeLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 120, -1, -1));

        eyeLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye2.png"))); // NOI18N
        eyeLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eyeLabel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                eyeLabel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                eyeLabel6MouseExited(evt);
            }
        });
        homePanel1.add(eyeLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(238, 320, -1, -1));

        total.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        total.setForeground(new java.awt.Color(255, 255, 255));
        total.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total.setText("000000");
        homePanel1.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 480, 150, -1));

        start.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        start.setForeground(new java.awt.Color(255, 255, 255));
        start.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        start.setText("start");
        start.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                startMouseClicked(evt);
            }
        });
        homePanel1.add(start, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 630, 70, -1));

        end.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        end.setForeground(new java.awt.Color(255, 255, 255));
        end.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        end.setText("end");
        end.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                endMouseClicked(evt);
            }
        });
        homePanel1.add(end, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 630, 60, -1));

        shiftShow.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        shiftShow.setForeground(new java.awt.Color(255, 255, 255));
        shiftShow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        shiftShow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye2.png"))); // NOI18N
        shiftShow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                shiftShowMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                shiftShowMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                shiftShowMouseExited(evt);
            }
        });
        homePanel1.add(shiftShow, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 520, 50, -1));

        dayShow.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        dayShow.setForeground(new java.awt.Color(255, 255, 255));
        dayShow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dayShow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye2.png"))); // NOI18N
        dayShow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dayShowMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                dayShowMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                dayShowMouseExited(evt);
            }
        });
        homePanel1.add(dayShow, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 520, -1, -1));

        start1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        start1.setForeground(new java.awt.Color(255, 255, 255));
        start1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        start1.setText("start");
        start1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                start1MouseClicked(evt);
            }
        });
        homePanel1.add(start1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 620, 70, -1));

        total2.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        total2.setForeground(new java.awt.Color(255, 255, 255));
        total2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total2.setText("000000");
        homePanel1.add(total2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 480, 150, -1));

        end1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        end1.setForeground(new java.awt.Color(255, 255, 255));
        end1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        end1.setText("end");
        end1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                end1MouseClicked(evt);
            }
        });
        homePanel1.add(end1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 620, 60, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(44, 62, 80));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Day");
        homePanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 170, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(44, 62, 80));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Shift");
        homePanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 420, 160, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/light_gray.png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        homePanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 570, -1, -1));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/light_gray.png"))); // NOI18N
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        homePanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 570, -1, -1));

        homePanel.add(homePanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 530, 680));

        homePanel2.setBackground(new java.awt.Color(245, 246, 250));
        homePanel2.setLayout(null);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        Home0.setAutoCreateRowSorter(true);
        Home0.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Home0.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        Home0.setToolTipText("");
        Home0.setGridColor(new java.awt.Color(153, 153, 153));
        Home0.setRowHeight(45);
        Home0.setRowMargin(3);
        Home0.setShowHorizontalLines(true);
        Home0.setShowVerticalLines(false);
        Home0.setSurrendersFocusOnKeystroke(true);
        jScrollPane1.setViewportView(Home0);

        homePanel2.add(jScrollPane1);
        jScrollPane1.setBounds(0, 0, 550, 680);

        jScrollPane5.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        Home1.setAutoCreateRowSorter(true);
        Home1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Home1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        Home1.setEnabled(false);
        Home1.setGridColor(new java.awt.Color(153, 153, 153));
        Home1.setRowHeight(45);
        Home1.setShowHorizontalLines(true);
        Home1.setSurrendersFocusOnKeystroke(true);
        jScrollPane5.setViewportView(Home1);

        homePanel2.add(jScrollPane5);
        jScrollPane5.setBounds(0, 0, 550, 680);

        jScrollPane8.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        Home2.setAutoCreateRowSorter(true);
        Home2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Home2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        Home2.setEnabled(false);
        Home2.setGridColor(new java.awt.Color(153, 153, 153));
        Home2.setRowHeight(45);
        Home2.setShowHorizontalLines(true);
        Home2.setSurrendersFocusOnKeystroke(true);
        jScrollPane8.setViewportView(Home2);

        homePanel2.add(jScrollPane8);
        jScrollPane8.setBounds(0, 0, 550, 680);

        jScrollPane9.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        Home3.setAutoCreateRowSorter(true);
        Home3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Home3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        Home3.setEnabled(false);
        Home3.setGridColor(new java.awt.Color(153, 153, 153));
        Home3.setRowHeight(45);
        Home3.setShowHorizontalLines(true);
        Home3.setSurrendersFocusOnKeystroke(true);
        jScrollPane9.setViewportView(Home3);

        homePanel2.add(jScrollPane9);
        jScrollPane9.setBounds(0, 0, 550, 680);

        jScrollPane11.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane11.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        Home4.setAutoCreateRowSorter(true);
        Home4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Home4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        Home4.setEnabled(false);
        Home4.setGridColor(new java.awt.Color(153, 153, 153));
        Home4.setRowHeight(45);
        Home4.setShowHorizontalLines(true);
        Home4.setSurrendersFocusOnKeystroke(true);
        jScrollPane11.setViewportView(Home4);

        homePanel2.add(jScrollPane11);
        jScrollPane11.setBounds(0, 0, 550, 680);

        jScrollPane12.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane12.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        Home5.setAutoCreateRowSorter(true);
        Home5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Home5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        Home5.setEnabled(false);
        Home5.setGridColor(new java.awt.Color(153, 153, 153));
        Home5.setRowHeight(45);
        Home5.setShowHorizontalLines(true);
        Home5.setSurrendersFocusOnKeystroke(true);
        jScrollPane12.setViewportView(Home5);

        homePanel2.add(jScrollPane12);
        jScrollPane12.setBounds(0, 0, 550, 680);

        jScrollPane20.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane20.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        Home6.setAutoCreateRowSorter(true);
        Home6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Home6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        Home6.setEnabled(false);
        Home6.setGridColor(new java.awt.Color(153, 153, 153));
        Home6.setRowHeight(45);
        Home6.setShowHorizontalLines(true);
        Home6.setSurrendersFocusOnKeystroke(true);
        jScrollPane20.setViewportView(Home6);

        homePanel2.add(jScrollPane20);
        jScrollPane20.setBounds(0, 0, 550, 680);

        homePanel.add(homePanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, 550, 680));

        fullPanel.add(homePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        stockPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        stockPanel1.setBackground(new java.awt.Color(113, 128, 147));
        stockPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        itemPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        id_lbl2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        id_lbl2.setText("ID :");
        itemPanel2.add(id_lbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 70, 50));

        quan_lbl2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        quan_lbl2.setText("Quantity :");
        itemPanel2.add(quan_lbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 110, 50));

        price_lbl2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        price_lbl2.setText("Purchase Price :");
        itemPanel2.add(price_lbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, -1, 50));

        stockpurchasesPrice.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        stockpurchasesPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                stockpurchasesPriceKeyTyped(evt);
            }
        });
        itemPanel2.add(stockpurchasesPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 280, 290, 50));

        name_lbl2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        name_lbl2.setText("Item :");
        itemPanel2.add(name_lbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 80, 50));

        stock_name.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        stock_name.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                stock_nameCaretUpdate(evt);
            }
        });
        stock_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                stock_nameKeyTyped(evt);
            }
        });
        itemPanel2.add(stock_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 290, 50));

        stock_id.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        stock_id.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                stock_idKeyPressed(evt);
            }
        });
        itemPanel2.add(stock_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 290, 50));

        stock_quan.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        stock_quan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                stock_quanKeyTyped(evt);
            }
        });
        itemPanel2.add(stock_quan, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 220, 290, 50));

        branch_lbl2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        branch_lbl2.setText("Branch :");
        itemPanel2.add(branch_lbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 110, 50));

        alarmRange.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        alarmRange.setText("10");
        itemPanel2.add(alarmRange, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 400, 290, 50));

        price_lbl6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        price_lbl6.setText("Sales Price :");
        itemPanel2.add(price_lbl6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, 130, 50));

        stocksalePrice.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        stocksalePrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                stocksalePriceKeyTyped(evt);
            }
        });
        itemPanel2.add(stocksalePrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, 290, 50));

        alarm_lbl2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        alarm_lbl2.setText("Alarm Range :");
        itemPanel2.add(alarm_lbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, 140, 30));

        stockComboBox.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        stockComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockComboBoxActionPerformed(evt);
            }
        });
        itemPanel2.add(stockComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 290, 50));

        stockPanel1.add(itemPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 510, 480));

        view_icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewD.png"))); // NOI18N
        view_icon2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                view_icon2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view_icon2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view_icon2MouseExited(evt);
            }
        });
        stockPanel1.add(view_icon2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 520, 50, 60));

        view_lbl2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        view_lbl2.setForeground(new java.awt.Color(30, 34, 43));
        view_lbl2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        view_lbl2.setText("View");
        stockPanel1.add(view_lbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 590, 50, 30));

        add_icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addD.png"))); // NOI18N
        add_icon2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                add_icon2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                add_icon2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                add_icon2MouseExited(evt);
            }
        });
        stockPanel1.add(add_icon2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 520, 50, 60));

        add_lbl2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        add_lbl2.setForeground(new java.awt.Color(30, 34, 43));
        add_lbl2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add_lbl2.setText("Add");
        stockPanel1.add(add_lbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(124, 590, 60, 30));

        delete_icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteD.png"))); // NOI18N
        delete_icon2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                delete_icon2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                delete_icon2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                delete_icon2MouseExited(evt);
            }
        });
        stockPanel1.add(delete_icon2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 520, 50, 60));

        del_lbl2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        del_lbl2.setForeground(new java.awt.Color(30, 34, 43));
        del_lbl2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        del_lbl2.setText("Delete");
        stockPanel1.add(del_lbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 590, 70, 30));

        edit_icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editD.png"))); // NOI18N
        edit_icon2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                edit_icon2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                edit_icon2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                edit_icon2MouseExited(evt);
            }
        });
        stockPanel1.add(edit_icon2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 520, 50, 60));

        edit_lbl2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        edit_lbl2.setForeground(new java.awt.Color(30, 34, 43));
        edit_lbl2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        edit_lbl2.setText("Edit");
        stockPanel1.add(edit_lbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 590, 50, 30));

        clear_icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearD.png"))); // NOI18N
        clear_icon2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clear_icon2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clear_icon2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                clear_icon2MouseExited(evt);
            }
        });
        stockPanel1.add(clear_icon2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 520, 50, 60));

        search_lbl2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        search_lbl2.setForeground(new java.awt.Color(30, 34, 43));
        search_lbl2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        search_lbl2.setText("Clear");
        stockPanel1.add(search_lbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(422, 590, 70, 30));

        stockPanel.add(stockPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 530, 680));

        stockPanel2.setBackground(new java.awt.Color(245, 246, 250));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jTable2.setAutoCreateRowSorter(true);
        jTable2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable2.setGridColor(new java.awt.Color(153, 153, 153));
        jTable2.setIntercellSpacing(new java.awt.Dimension(0, 0));
        jTable2.setRowHeight(45);
        jTable2.setShowHorizontalLines(true);
        jTable2.setShowVerticalLines(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout stockPanel2Layout = new javax.swing.GroupLayout(stockPanel2);
        stockPanel2.setLayout(stockPanel2Layout);
        stockPanel2Layout.setHorizontalGroup(
            stockPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        stockPanel2Layout.setVerticalGroup(
            stockPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
        );

        stockPanel.add(stockPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, 550, 680));

        fullPanel.add(stockPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        salesPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        salesPanel1.setBackground(new java.awt.Color(113, 128, 147));
        salesPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        itemPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ch_lbl3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ch_lbl3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ch_lbl3.setText("Check ID :");
        ch_lbl3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        itemPanel3.add(ch_lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 110, 100, 40));

        salesCheckId.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        salesCheckId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salesCheckIdActionPerformed(evt);
            }
        });
        salesCheckId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                salesCheckIdKeyPressed(evt);
            }
        });
        itemPanel3.add(salesCheckId, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 340, 40));

        salesStockName.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        itemPanel3.add(salesStockName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 280, 120, 50));

        quan_lbl3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        quan_lbl3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        quan_lbl3.setText("Quantity :");
        quan_lbl3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        itemPanel3.add(quan_lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 340, 120, 50));

        salesQuant.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        itemPanel3.add(salesQuant, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 340, 120, 50));

        price_lbl3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        price_lbl3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        price_lbl3.setText("Price :");
        price_lbl3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        itemPanel3.add(price_lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 340, 70, 50));

        salesClient.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        itemPanel3.add(salesClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 340, 50));

        cName_lbl3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cName_lbl3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cName_lbl3.setText("Client Name :");
        cName_lbl3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        itemPanel3.add(cName_lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 160, 120, 50));

        salesPrice.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        itemPanel3.add(salesPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 340, 130, 50));

        name_lbl3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        name_lbl3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        name_lbl3.setText("ID :");
        name_lbl3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        itemPanel3.add(name_lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 280, 60, 50));

        salestotal.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        salestotal.setForeground(new java.awt.Color(255, 51, 51));
        salestotal.setEnabled(false);
        salestotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salestotalActionPerformed(evt);
            }
        });
        itemPanel3.add(salestotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 400, 340, 50));

        total_lbl3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        total_lbl3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        total_lbl3.setText("Total :");
        total_lbl3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        itemPanel3.add(total_lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 400, 100, 50));

        salespaid.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        salespaid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                salespaidFocusGained(evt);
            }
        });
        itemPanel3.add(salespaid, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 460, 110, 50));

        paid_lbl3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        paid_lbl3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        paid_lbl3.setText("Paid :");
        paid_lbl3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        itemPanel3.add(paid_lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 460, 110, 50));

        salesRest.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        salesRest.setForeground(new java.awt.Color(255, 51, 51));
        salesRest.setEnabled(false);
        salesRest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salesRestActionPerformed(evt);
            }
        });
        itemPanel3.add(salesRest, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 460, 100, 50));

        rest_lbl3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        rest_lbl3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rest_lbl3.setText("Rest :");
        rest_lbl3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        itemPanel3.add(rest_lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 460, 60, 50));

        salesMob.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        salesMob.setText("01");
        itemPanel3.add(salesMob, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, 340, 50));

        mob_lbl3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        mob_lbl3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mob_lbl3.setText("Mobile :");
        mob_lbl3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        itemPanel3.add(mob_lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 220, 80, 50));

        userSales.setEditable(false);
        userSales.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        userSales.setText(getUser());
        userSales.setEnabled(false);
        itemPanel3.add(userSales, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 130, 40));

        mob_lbl6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        mob_lbl6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mob_lbl6.setText("User :");
        mob_lbl6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        itemPanel3.add(mob_lbl6, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 5, 90, 50));

        salesDate.setEditable(false);
        salesDate.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        salesDate.setForeground(new java.awt.Color(255, 51, 51));
        salesDate.setText(String.valueOf(LocalDate.now()));
        salesDate.setEnabled(false);
        salesDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salesDateActionPerformed(evt);
            }
        });
        itemPanel3.add(salesDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 340, 40));

        rest_lbl5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        rest_lbl5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rest_lbl5.setText("Date :");
        rest_lbl5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        itemPanel3.add(rest_lbl5, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 60, 100, 40));

        branch_lbl3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        branch_lbl3.setText("Branch :");
        itemPanel3.add(branch_lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 80, 40));

        salesComboBox.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        salesComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salesComboBoxActionPerformed(evt);
            }
        });
        itemPanel3.add(salesComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 105, 40));

        multiple_sales_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/multiple_dark.png"))); // NOI18N
        multiple_sales_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                multiple_sales_lblMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                multiple_sales_lblMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                multiple_sales_lblMouseExited(evt);
            }
        });
        itemPanel3.add(multiple_sales_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 6, 30, 40));

        salesStockId.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        salesStockId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                salesStockIdKeyPressed(evt);
            }
        });
        itemPanel3.add(salesStockId, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 280, 130, 50));

        item_lbl3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        item_lbl3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        item_lbl3.setText("Item :");
        item_lbl3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        itemPanel3.add(item_lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 280, 110, 50));
        itemPanel3.add(deptBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(485, 475, 20, -1));

        dept_lbl00.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        dept_lbl00.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        dept_lbl00.setText("Dept");
        itemPanel3.add(dept_lbl00, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 460, 50, 50));

        salesPanel1.add(itemPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 510, 520));

        view_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewD.png"))); // NOI18N
        view_icon3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                view_icon3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view_icon3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view_icon3MouseExited(evt);
            }
        });
        salesPanel1.add(view_icon3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 540, 50, 60));

        view_lbl3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        view_lbl3.setForeground(new java.awt.Color(30, 34, 43));
        view_lbl3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        view_lbl3.setText("View");
        salesPanel1.add(view_lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 600, 50, 30));

        add_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addD.png"))); // NOI18N
        add_icon3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                add_icon3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                add_icon3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                add_icon3MouseExited(evt);
            }
        });
        salesPanel1.add(add_icon3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 540, 50, 60));

        add_lbl3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        add_lbl3.setForeground(new java.awt.Color(30, 34, 43));
        add_lbl3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add_lbl3.setText("Add");
        salesPanel1.add(add_lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 600, 60, 30));

        delete_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteD.png"))); // NOI18N
        delete_icon3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                delete_icon3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                delete_icon3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                delete_icon3MouseExited(evt);
            }
        });
        salesPanel1.add(delete_icon3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 630, 50, 60));

        del_lbl3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        del_lbl3.setForeground(new java.awt.Color(30, 34, 43));
        del_lbl3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        del_lbl3.setText("Delete");
        salesPanel1.add(del_lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 650, 70, 30));

        edit_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editD.png"))); // NOI18N
        edit_icon3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                edit_icon3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                edit_icon3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                edit_icon3MouseExited(evt);
            }
        });
        salesPanel1.add(edit_icon3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 630, 50, 60));

        edit_lbl3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        edit_lbl3.setForeground(new java.awt.Color(30, 34, 43));
        edit_lbl3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        edit_lbl3.setText("Edit");
        salesPanel1.add(edit_lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 650, 50, 30));

        clear_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearD.png"))); // NOI18N
        clear_icon3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clear_icon3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clear_icon3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                clear_icon3MouseExited(evt);
            }
        });
        salesPanel1.add(clear_icon3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 540, 50, 60));

        search_lbl3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        search_lbl3.setForeground(new java.awt.Color(30, 34, 43));
        search_lbl3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        search_lbl3.setText("Clear");
        salesPanel1.add(search_lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 600, 70, 30));

        salesPanel.add(salesPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 530, 680));

        salesPanel2.setBackground(new java.awt.Color(245, 246, 250));

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        salesT.setAutoCreateRowSorter(true);
        salesT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        salesT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        salesT.setGridColor(new java.awt.Color(153, 153, 153));
        salesT.setIntercellSpacing(new java.awt.Dimension(0, 0));
        salesT.setRowHeight(45);
        salesT.setShowHorizontalLines(true);
        salesT.setShowVerticalLines(false);
        salesT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                salesTMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(salesT);

        javax.swing.GroupLayout salesPanel2Layout = new javax.swing.GroupLayout(salesPanel2);
        salesPanel2.setLayout(salesPanel2Layout);
        salesPanel2Layout.setHorizontalGroup(
            salesPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(salesPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        salesPanel2Layout.setVerticalGroup(
            salesPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
        );

        salesPanel.add(salesPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, 550, 680));

        fullPanel.add(salesPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        paymentPanel1.setBackground(new java.awt.Color(113, 128, 147));
        paymentPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        itemPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ch_lbl4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ch_lbl4.setText("Check ID :");
        itemPanel4.add(ch_lbl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 110, 40));

        totalPayment.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        totalPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalPaymentActionPerformed(evt);
            }
        });
        itemPanel4.add(totalPayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 300, 340, 50));

        rest_lbl4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        rest_lbl4.setText("Rest :");
        itemPanel4.add(rest_lbl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 360, 60, 50));

        namePayment.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        itemPanel4.add(namePayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, 340, 50));

        checkIdPayment.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        checkIdPayment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                checkIdPaymentKeyPressed(evt);
            }
        });
        itemPanel4.add(checkIdPayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 210, 40));

        paid_lbl4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        paid_lbl4.setText("Paid :");
        itemPanel4.add(paid_lbl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 100, 50));

        notes_lbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        notes_lbl.setText("Notes :");
        itemPanel4.add(notes_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 80, 50));

        restPayment.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        restPayment.setForeground(new java.awt.Color(255, 51, 51));
        restPayment.setEnabled(false);
        restPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restPaymentActionPerformed(evt);
            }
        });
        itemPanel4.add(restPayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 360, 100, 50));

        clientNamePayment.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        itemPanel4.add(clientNamePayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 340, 50));

        paidPayment.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        paidPayment.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                paidPaymentFocusGained(evt);
            }
        });
        paidPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paidPaymentActionPerformed(evt);
            }
        });
        itemPanel4.add(paidPayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 360, 110, 50));

        cName_lbl4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cName_lbl4.setText("Client Name :");
        itemPanel4.add(cName_lbl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 120, 50));

        name_lbl4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        name_lbl4.setText("Item :");
        itemPanel4.add(name_lbl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 80, 50));

        mobilePayment.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        itemPanel4.add(mobilePayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 340, 50));

        mob_lbl4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        mob_lbl4.setText("Mobile :");
        itemPanel4.add(mob_lbl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 80, 50));

        UserPayment.setEditable(false);
        UserPayment.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        UserPayment.setText(getUser());
        UserPayment.setEnabled(false);
        itemPanel4.add(UserPayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 150, 40));

        mob_lbl5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        mob_lbl5.setText("User :");
        itemPanel4.add(mob_lbl5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 110, 50));

        paymentDate.setEditable(false);
        paymentDate.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        paymentDate.setForeground(new java.awt.Color(255, 51, 51));
        paymentDate.setText(String.valueOf(LocalDate.now()));
        paymentDate.setEnabled(false);
        itemPanel4.add(paymentDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, 130, 40));

        date_lbl4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        date_lbl4.setText("Date :");
        itemPanel4.add(date_lbl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 70, 50));

        paymentBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentBox1ActionPerformed(evt);
            }
        });
        itemPanel4.add(paymentBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(465, 75, -1, -1));
        itemPanel4.add(deptBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 375, -1, -1));

        notes.setColumns(20);
        notes.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        notes.setRows(5);
        jScrollPane19.setViewportView(notes);

        itemPanel4.add(jScrollPane19, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 420, 340, 90));

        total_lbl9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        total_lbl9.setText("Total :");
        itemPanel4.add(total_lbl9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 80, 50));

        payment_lbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        payment_lbl.setText("Payment");
        itemPanel4.add(payment_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 60, 100, 50));

        dept_lbl01.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        dept_lbl01.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        dept_lbl01.setText("Dept");
        itemPanel4.add(dept_lbl01, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 360, 50, 50));

        paymentPanel1.add(itemPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 510, 520));

        view_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewD.png"))); // NOI18N
        view_icon4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                view_icon4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view_icon4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view_icon4MouseExited(evt);
            }
        });
        paymentPanel1.add(view_icon4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 540, 50, 60));

        view_lbl4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        view_lbl4.setForeground(new java.awt.Color(30, 34, 43));
        view_lbl4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        view_lbl4.setText("View");
        paymentPanel1.add(view_lbl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 600, 50, 30));

        add_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addD.png"))); // NOI18N
        add_icon4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                add_icon4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                add_icon4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                add_icon4MouseExited(evt);
            }
        });
        paymentPanel1.add(add_icon4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 540, 50, 60));

        add_lbl4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        add_lbl4.setForeground(new java.awt.Color(30, 34, 43));
        add_lbl4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add_lbl4.setText("Add");
        paymentPanel1.add(add_lbl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 600, 60, 30));

        delete_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteD.png"))); // NOI18N
        delete_icon4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                delete_icon4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                delete_icon4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                delete_icon4MouseExited(evt);
            }
        });
        paymentPanel1.add(delete_icon4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 630, 50, 60));

        del_lbl4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        del_lbl4.setForeground(new java.awt.Color(30, 34, 43));
        del_lbl4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        del_lbl4.setText("Delete");
        paymentPanel1.add(del_lbl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 650, 70, 30));

        edit_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editD.png"))); // NOI18N
        edit_icon4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                edit_icon4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                edit_icon4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                edit_icon4MouseExited(evt);
            }
        });
        paymentPanel1.add(edit_icon4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 630, 50, 60));

        edit_lbl4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        edit_lbl4.setForeground(new java.awt.Color(30, 34, 43));
        edit_lbl4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        edit_lbl4.setText("Edit");
        paymentPanel1.add(edit_lbl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 650, 50, 30));

        clear_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearD.png"))); // NOI18N
        clear_icon4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clear_icon4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clear_icon4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                clear_icon4MouseExited(evt);
            }
        });
        paymentPanel1.add(clear_icon4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 540, 50, 60));

        search_lbl4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        search_lbl4.setForeground(new java.awt.Color(30, 34, 43));
        search_lbl4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        search_lbl4.setText("Clear");
        paymentPanel1.add(search_lbl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 600, 70, 30));

        paymentPanel2.setBackground(new java.awt.Color(245, 246, 250));

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        paymentT.setAutoCreateRowSorter(true);
        paymentT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        paymentT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        paymentT.setGridColor(new java.awt.Color(153, 153, 153));
        paymentT.setIntercellSpacing(new java.awt.Dimension(0, 0));
        paymentT.setRowHeight(45);
        paymentT.setShowHorizontalLines(true);
        paymentT.setShowVerticalLines(false);
        paymentT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paymentTMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(paymentT);

        javax.swing.GroupLayout paymentPanel2Layout = new javax.swing.GroupLayout(paymentPanel2);
        paymentPanel2.setLayout(paymentPanel2Layout);
        paymentPanel2Layout.setHorizontalGroup(
            paymentPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paymentPanel2Layout.setVerticalGroup(
            paymentPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout paymentPanelLayout = new javax.swing.GroupLayout(paymentPanel);
        paymentPanel.setLayout(paymentPanelLayout);
        paymentPanelLayout.setHorizontalGroup(
            paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentPanelLayout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(paymentPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(paymentPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        paymentPanelLayout.setVerticalGroup(
            paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentPanelLayout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(paymentPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paymentPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        fullPanel.add(paymentPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        deptPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        deptPanel1.setBackground(new java.awt.Color(113, 128, 147));
        deptPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/download.png"))); // NOI18N
        deptPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, -1, -1));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/upload.png"))); // NOI18N
        deptPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, -1, -1));

        myDeptL.setFont(new java.awt.Font("Segoe UI", 0, 60)); // NOI18N
        myDeptL.setForeground(new java.awt.Color(255, 255, 255));
        myDeptL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        myDeptL.setText("000000");
        deptPanel1.add(myDeptL, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 420, 480, -1));

        dept.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye2.png"))); // NOI18N
        dept.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deptMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deptMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deptMouseExited(evt);
            }
        });
        deptPanel1.add(dept, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 210, -1, -1));

        myDeptE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye2.png"))); // NOI18N
        myDeptE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                myDeptEMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                myDeptEMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                myDeptEMouseExited(evt);
            }
        });
        deptPanel1.add(myDeptE, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 500, -1, -1));

        deptL.setFont(new java.awt.Font("Segoe UI", 0, 60)); // NOI18N
        deptL.setForeground(new java.awt.Color(255, 255, 255));
        deptL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        deptL.setText("000000");
        deptPanel1.add(deptL, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 130, 480, -1));

        deptL1.setFont(new java.awt.Font("Segoe UI", 0, 60)); // NOI18N
        deptL1.setForeground(new java.awt.Color(53, 59, 72));
        deptL1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        deptL1.setText("Clients");
        deptPanel1.add(deptL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, -1, -1));

        deptL2.setFont(new java.awt.Font("Segoe UI", 0, 60)); // NOI18N
        deptL2.setForeground(new java.awt.Color(53, 59, 72));
        deptL2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        deptL2.setText("US");
        deptPanel1.add(deptL2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 340, -1, -1));

        deptPanel.add(deptPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 530, 680));

        deptPanel2.setBackground(new java.awt.Color(255, 255, 255));

        itemPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ch_lbl5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ch_lbl5.setText("Check ID :");
        itemPanel5.add(ch_lbl5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 21, 120, 50));

        Total.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        Total.setForeground(new java.awt.Color(255, 51, 51));
        Total.setEnabled(false);
        Total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TotalActionPerformed(evt);
            }
        });
        itemPanel5.add(Total, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, 330, 50));

        Name.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        itemPanel5.add(Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 330, 50));

        check_id.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        itemPanel5.add(check_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 330, 50));

        total_lbl5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        total_lbl5.setText("Dept :");
        itemPanel5.add(total_lbl5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 90, 50));

        client_name.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        client_name.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                client_nameCaretUpdate(evt);
            }
        });
        itemPanel5.add(client_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 330, 50));

        cName_lbl5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cName_lbl5.setText("Client Name :");
        itemPanel5.add(cName_lbl5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 120, 50));

        name_lbl5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        name_lbl5.setText("Item :");
        itemPanel5.add(name_lbl5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 90, 50));

        deptPaid.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        deptPaid.setForeground(new java.awt.Color(255, 51, 51));
        deptPaid.setEnabled(false);
        deptPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deptPaidActionPerformed(evt);
            }
        });
        itemPanel5.add(deptPaid, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 260, 330, 50));

        total_lbl6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        total_lbl6.setText("Paid :");
        itemPanel5.add(total_lbl6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 90, 50));

        jScrollPane10.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        deptT.setAutoCreateRowSorter(true);
        deptT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        deptT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        deptT.setGridColor(new java.awt.Color(153, 153, 153));
        deptT.setIntercellSpacing(new java.awt.Dimension(0, 0));
        deptT.setRowHeight(45);
        deptT.setShowHorizontalLines(true);
        deptT.setShowVerticalLines(false);
        deptT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deptTMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(deptT);

        javax.swing.GroupLayout deptPanel2Layout = new javax.swing.GroupLayout(deptPanel2);
        deptPanel2.setLayout(deptPanel2Layout);
        deptPanel2Layout.setHorizontalGroup(
            deptPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deptPanel2Layout.createSequentialGroup()
                .addComponent(itemPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
            .addGroup(deptPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(deptPanel2Layout.createSequentialGroup()
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        deptPanel2Layout.setVerticalGroup(
            deptPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deptPanel2Layout.createSequentialGroup()
                .addGap(0, 283, Short.MAX_VALUE)
                .addComponent(itemPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(deptPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(deptPanel2Layout.createSequentialGroup()
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 404, Short.MAX_VALUE)))
        );

        deptPanel.add(deptPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, 550, 680));

        fullPanel.add(deptPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        ConsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ConsPanel1.setBackground(new java.awt.Color(113, 128, 147));
        ConsPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        itemPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        id_lbl6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        id_lbl6.setText("ID :");
        itemPanel6.add(id_lbl6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 60, 50));

        quan_lbl6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        quan_lbl6.setText("Quantity :");
        itemPanel6.add(quan_lbl6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 100, 50));

        consId.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        itemPanel6.add(consId, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 290, 50));

        name_lbl6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        name_lbl6.setText("Item :");
        itemPanel6.add(name_lbl6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 100, 50));

        consName.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        consName.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                consNameCaretUpdate(evt);
            }
        });
        itemPanel6.add(consName, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 290, 50));

        consQuantity.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        itemPanel6.add(consQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, 290, 50));

        branch_lbl6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        branch_lbl6.setText("Branch :");
        itemPanel6.add(branch_lbl6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 90, 50));

        consComboBox.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        consComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consComboBoxActionPerformed(evt);
            }
        });
        itemPanel6.add(consComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 290, 50));

        ConsPanel1.add(itemPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 510, 290));

        view_icon6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewD.png"))); // NOI18N
        view_icon6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                view_icon6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view_icon6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view_icon6MouseExited(evt);
            }
        });
        ConsPanel1.add(view_icon6, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 520, 50, 60));

        view_lbl6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        view_lbl6.setForeground(new java.awt.Color(30, 34, 43));
        view_lbl6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        view_lbl6.setText("View");
        ConsPanel1.add(view_lbl6, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 580, 50, 30));

        clear_icon6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearD.png"))); // NOI18N
        clear_icon6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clear_icon6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clear_icon6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                clear_icon6MouseExited(evt);
            }
        });
        ConsPanel1.add(clear_icon6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 520, 50, 60));

        search_lbl6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        search_lbl6.setForeground(new java.awt.Color(30, 34, 43));
        search_lbl6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        search_lbl6.setText("Clear");
        ConsPanel1.add(search_lbl6, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 580, 70, 30));

        ConsPanel.add(ConsPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 530, 680));

        ConsPanel2.setBackground(new java.awt.Color(245, 246, 250));

        jScrollPane13.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane13.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        consT.setAutoCreateRowSorter(true);
        consT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        consT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        consT.setGridColor(new java.awt.Color(153, 153, 153));
        consT.setIntercellSpacing(new java.awt.Dimension(0, 0));
        consT.setRowHeight(45);
        consT.setShowHorizontalLines(true);
        consT.setShowVerticalLines(false);
        consT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                consTMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(consT);

        javax.swing.GroupLayout ConsPanel2Layout = new javax.swing.GroupLayout(ConsPanel2);
        ConsPanel2.setLayout(ConsPanel2Layout);
        ConsPanel2Layout.setHorizontalGroup(
            ConsPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConsPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        ConsPanel2Layout.setVerticalGroup(
            ConsPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
        );

        ConsPanel.add(ConsPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, 550, 680));

        fullPanel.add(ConsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        returnPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        returnPanel1.setBackground(new java.awt.Color(113, 128, 147));
        returnPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        itemPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        id_lbl7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        id_lbl7.setText("Check ID :");
        itemPanel7.add(id_lbl7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 110, 50));

        returnId.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        returnId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                returnIdKeyPressed(evt);
            }
        });
        itemPanel7.add(returnId, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 340, 50));

        returnQuantity.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        itemPanel7.add(returnQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 300, 110, 50));

        quan_lbl7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        quan_lbl7.setText("Quantity :");
        itemPanel7.add(quan_lbl7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, -1, 50));

        returnPrice.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        itemPanel7.add(returnPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 300, 130, 50));

        price_lbl7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        price_lbl7.setText("Price :");
        itemPanel7.add(price_lbl7, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 300, 70, 50));

        returnStockName.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        itemPanel7.add(returnStockName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, 110, 50));

        name_lbl7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        name_lbl7.setText("Item :");
        itemPanel7.add(name_lbl7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 70, 50));

        returnClient.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        itemPanel7.add(returnClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 340, 50));

        cName_lbl7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cName_lbl7.setText("Client Name :");
        itemPanel7.add(cName_lbl7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 120, 50));

        returnTotal.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        returnTotal.setForeground(new java.awt.Color(255, 51, 51));
        returnTotal.setEnabled(false);
        returnTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnTotalActionPerformed(evt);
            }
        });
        itemPanel7.add(returnTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 360, 340, 50));

        total_lbl7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        total_lbl7.setText("Total :");
        itemPanel7.add(total_lbl7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 100, 50));

        returnPaid.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        returnPaid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                returnPaidFocusGained(evt);
            }
        });
        itemPanel7.add(returnPaid, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 420, 110, 50));

        paid_lbl7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        paid_lbl7.setText("Paid :");
        itemPanel7.add(paid_lbl7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 110, 50));

        returnRest.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        returnRest.setForeground(new java.awt.Color(255, 51, 51));
        returnRest.setEnabled(false);
        itemPanel7.add(returnRest, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 420, 100, 50));

        rest_lbl7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        rest_lbl7.setText("Rest :");
        itemPanel7.add(rest_lbl7, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 420, 60, 50));

        returnClient1.setEditable(false);
        returnClient1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        returnClient1.setText(getUser());
        returnClient1.setEnabled(false);
        returnClient1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                returnClient1CaretUpdate(evt);
            }
        });
        itemPanel7.add(returnClient1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 130, 40));

        cName_lbl9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cName_lbl9.setText("User :");
        itemPanel7.add(cName_lbl9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 90, 40));

        returnsDate.setEditable(false);
        returnsDate.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        returnsDate.setForeground(new java.awt.Color(255, 51, 51));
        returnsDate.setText(String.valueOf(LocalDate.now()));
        returnsDate.setEnabled(false);
        returnsDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnsDateActionPerformed(evt);
            }
        });
        itemPanel7.add(returnsDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 340, 40));

        date_lbl7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        date_lbl7.setText("Date :");
        itemPanel7.add(date_lbl7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 90, 40));

        returnComboBox.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        returnComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnComboBoxActionPerformed(evt);
            }
        });
        itemPanel7.add(returnComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 120, 40));

        branch_lbl7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        branch_lbl7.setText("Branch :");
        itemPanel7.add(branch_lbl7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 80, 40));
        itemPanel7.add(deptBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 435, 30, -1));

        multiple_return_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/multiple_dark.png"))); // NOI18N
        multiple_return_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                multiple_return_lblMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                multiple_return_lblMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                multiple_return_lblMouseExited(evt);
            }
        });
        itemPanel7.add(multiple_return_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 6, 30, 40));

        returnStrockId.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        returnStrockId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                returnStrockIdKeyPressed(evt);
            }
        });
        itemPanel7.add(returnStrockId, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 240, 130, 50));

        id2_lbl9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        id2_lbl9.setText("ID :");
        itemPanel7.add(id2_lbl9, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 240, 80, 50));

        return_dept_lbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        return_dept_lbl.setText("Dept");
        itemPanel7.add(return_dept_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 420, 50, 50));

        returnPanel1.add(itemPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 510, 490));

        view_icon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewD.png"))); // NOI18N
        view_icon7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                view_icon7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view_icon7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view_icon7MouseExited(evt);
            }
        });
        returnPanel1.add(view_icon7, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 540, 50, 60));

        view_lbl7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        view_lbl7.setForeground(new java.awt.Color(30, 34, 43));
        view_lbl7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        view_lbl7.setText("View");
        returnPanel1.add(view_lbl7, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 600, 50, 30));

        add_icon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addD.png"))); // NOI18N
        add_icon7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                add_icon7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                add_icon7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                add_icon7MouseExited(evt);
            }
        });
        returnPanel1.add(add_icon7, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 540, 50, 60));

        add_lbl7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        add_lbl7.setForeground(new java.awt.Color(30, 34, 43));
        add_lbl7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add_lbl7.setText("Add");
        returnPanel1.add(add_lbl7, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 600, 70, 30));

        clear_icon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearD.png"))); // NOI18N
        clear_icon7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clear_icon7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clear_icon7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                clear_icon7MouseExited(evt);
            }
        });
        returnPanel1.add(clear_icon7, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 540, 50, 60));

        search_lbl7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        search_lbl7.setForeground(new java.awt.Color(30, 34, 43));
        search_lbl7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        search_lbl7.setText("Clear");
        returnPanel1.add(search_lbl7, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 600, 70, 30));

        returnPanel.add(returnPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 530, 680));

        returnPanel2.setBackground(new java.awt.Color(245, 246, 250));

        jScrollPane14.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane14.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        returnT.setAutoCreateRowSorter(true);
        returnT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        returnT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        returnT.setGridColor(new java.awt.Color(153, 153, 153));
        returnT.setIntercellSpacing(new java.awt.Dimension(0, 0));
        returnT.setRowHeight(45);
        returnT.setShowHorizontalLines(true);
        returnT.setShowVerticalLines(false);
        returnT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                returnTMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(returnT);

        javax.swing.GroupLayout returnPanel2Layout = new javax.swing.GroupLayout(returnPanel2);
        returnPanel2.setLayout(returnPanel2Layout);
        returnPanel2Layout.setHorizontalGroup(
            returnPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(returnPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        returnPanel2Layout.setVerticalGroup(
            returnPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
        );

        returnPanel.add(returnPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, 550, 680));

        fullPanel.add(returnPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        orderPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        orderPanel1.setBackground(new java.awt.Color(113, 128, 147));
        orderPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        itemPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        id_lbl8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        id_lbl8.setText("ID :");
        itemPanel8.add(id_lbl8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 100, 50));

        orderId.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        itemPanel8.add(orderId, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 340, 50));

        orderQuantity.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        itemPanel8.add(orderQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, 110, 50));

        quan_lbl8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        quan_lbl8.setText("Quantity :");
        itemPanel8.add(quan_lbl8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 100, 50));

        orderPrice.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        itemPanel8.add(orderPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 310, 140, 50));

        price_lbl8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        price_lbl8.setText("Price :");
        itemPanel8.add(price_lbl8, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 310, 60, 50));

        orderName.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        orderName.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                orderNameCaretUpdate(evt);
            }
        });
        itemPanel8.add(orderName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, 340, 50));

        name_lbl8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        name_lbl8.setText("Item :");
        itemPanel8.add(name_lbl8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 90, 50));

        orderClient.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        orderClient.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                orderClientCaretUpdate(evt);
            }
        });
        itemPanel8.add(orderClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 340, 50));

        cName_lbl8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cName_lbl8.setText("Client Name :");
        itemPanel8.add(cName_lbl8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, 50));

        orderTotal.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        orderTotal.setForeground(new java.awt.Color(255, 51, 51));
        orderTotal.setEnabled(false);
        orderTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderTotalActionPerformed(evt);
            }
        });
        itemPanel8.add(orderTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 370, 340, 50));

        total_lbl8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        total_lbl8.setText("Total :");
        itemPanel8.add(total_lbl8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 80, 40));

        orderPaid.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        orderPaid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                orderPaidFocusGained(evt);
            }
        });
        itemPanel8.add(orderPaid, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 430, 110, 50));

        paid_lbl8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        paid_lbl8.setText("Paid :");
        itemPanel8.add(paid_lbl8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 100, 50));

        orderRest.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        orderRest.setForeground(new java.awt.Color(255, 51, 51));
        orderRest.setEnabled(false);
        orderRest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderRestActionPerformed(evt);
            }
        });
        itemPanel8.add(orderRest, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 430, 100, 50));

        rest_lbl8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        rest_lbl8.setText("Rest :");
        itemPanel8.add(rest_lbl8, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 430, 60, 50));

        orderClient1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        orderClient1.setText(getUser());
        orderClient1.setEnabled(false);
        orderClient1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                orderClient1CaretUpdate(evt);
            }
        });
        itemPanel8.add(orderClient1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 340, 50));

        cName_lbl10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cName_lbl10.setText("User :");
        itemPanel8.add(cName_lbl10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 90, 50));

        ordersDate3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        ordersDate3.setForeground(new java.awt.Color(255, 51, 51));
        ordersDate3.setText(String.valueOf(LocalDate.now()));
        ordersDate3.setEnabled(false);
        ordersDate3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ordersDate3ActionPerformed(evt);
            }
        });
        itemPanel8.add(ordersDate3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 340, 50));

        rest_lbl10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        rest_lbl10.setText("Date :");
        itemPanel8.add(rest_lbl10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 100, 50));
        itemPanel8.add(deptBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 445, -1, -1));

        order_dept_lbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        order_dept_lbl.setText("Dept");
        itemPanel8.add(order_dept_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 430, 50, 50));

        orderPanel1.add(itemPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 510, 500));

        view_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewD.png"))); // NOI18N
        view_icon8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                view_icon8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view_icon8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view_icon8MouseExited(evt);
            }
        });
        orderPanel1.add(view_icon8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 520, 50, 60));

        view_lbl8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        view_lbl8.setForeground(new java.awt.Color(30, 34, 43));
        view_lbl8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        view_lbl8.setText("View");
        orderPanel1.add(view_lbl8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 580, 50, 30));

        add_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addD.png"))); // NOI18N
        add_icon8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                add_icon8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                add_icon8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                add_icon8MouseExited(evt);
            }
        });
        orderPanel1.add(add_icon8, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 520, 50, 60));

        add_lbl8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        add_lbl8.setForeground(new java.awt.Color(30, 34, 43));
        add_lbl8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add_lbl8.setText("Add");
        orderPanel1.add(add_lbl8, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 580, 60, 30));

        delete_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteD.png"))); // NOI18N
        delete_icon8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                delete_icon8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                delete_icon8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                delete_icon8MouseExited(evt);
            }
        });
        orderPanel1.add(delete_icon8, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 520, 50, 60));

        del_lbl8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        del_lbl8.setForeground(new java.awt.Color(30, 34, 43));
        del_lbl8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        del_lbl8.setText("Delete");
        orderPanel1.add(del_lbl8, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 580, 70, 30));

        edit_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editD.png"))); // NOI18N
        edit_icon8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                edit_icon8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                edit_icon8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                edit_icon8MouseExited(evt);
            }
        });
        orderPanel1.add(edit_icon8, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 520, 50, 60));

        edit_lbl8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        edit_lbl8.setForeground(new java.awt.Color(30, 34, 43));
        edit_lbl8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        edit_lbl8.setText("Edit");
        orderPanel1.add(edit_lbl8, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 580, 50, 30));

        clear_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearD.png"))); // NOI18N
        clear_icon8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clear_icon8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clear_icon8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                clear_icon8MouseExited(evt);
            }
        });
        orderPanel1.add(clear_icon8, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 520, 50, 60));

        search_lbl8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        search_lbl8.setForeground(new java.awt.Color(30, 34, 43));
        search_lbl8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        search_lbl8.setText("Clear");
        orderPanel1.add(search_lbl8, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 580, 70, 30));

        orderPanel.add(orderPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 530, 680));

        orderPanel2.setBackground(new java.awt.Color(245, 246, 250));

        jScrollPane15.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane15.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        orderT.setAutoCreateRowSorter(true);
        orderT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        orderT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        orderT.setGridColor(new java.awt.Color(153, 153, 153));
        orderT.setIntercellSpacing(new java.awt.Dimension(0, 0));
        orderT.setRowHeight(45);
        orderT.setShowHorizontalLines(true);
        orderT.setShowVerticalLines(false);
        orderT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orderTMouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(orderT);

        javax.swing.GroupLayout orderPanel2Layout = new javax.swing.GroupLayout(orderPanel2);
        orderPanel2.setLayout(orderPanel2Layout);
        orderPanel2Layout.setHorizontalGroup(
            orderPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(orderPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        orderPanel2Layout.setVerticalGroup(
            orderPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
        );

        orderPanel.add(orderPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, -1, 680));

        fullPanel.add(orderPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        profitPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        decideImgLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thumb_up.png"))); // NOI18N
        profitPanel.add(decideImgLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 200, -1, -1));

        profitPanel1.setBackground(new java.awt.Color(113, 128, 147));
        profitPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/in.png"))); // NOI18N
        profitPanel1.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, -1, -1));

        yearLbl1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/year_white.png"))); // NOI18N
        yearLbl1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                yearLbl1MousePressed(evt);
            }
        });
        profitPanel1.add(yearLbl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 520, -1, -1));

        dayLbl1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/day_white.png"))); // NOI18N
        dayLbl1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                dayLbl1MousePressed(evt);
            }
        });
        profitPanel1.add(dayLbl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 520, -1, -1));

        monthLbl1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/month_white.png"))); // NOI18N
        monthLbl1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                monthLbl1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                monthLbl1MousePressed(evt);
            }
        });
        profitPanel1.add(monthLbl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 520, -1, -1));

        inLabel.setFont(new java.awt.Font("Segoe UI", 0, 100)); // NOI18N
        inLabel.setForeground(new java.awt.Color(255, 255, 255));
        inLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inLabel.setText("000000");
        profitPanel1.add(inLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 490, -1));

        inLabel4.setFont(new java.awt.Font("Segoe UI", 1, 60)); // NOI18N
        inLabel4.setForeground(new java.awt.Color(53, 59, 72));
        inLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inLabel4.setText("Income");
        profitPanel1.add(inLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 330, -1));

        from_lbl1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        from_lbl1.setForeground(new java.awt.Color(255, 255, 255));
        from_lbl1.setText("From :");
        profitPanel1.add(from_lbl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, -1, 60));

        dateChooserCombo4.setCurrentView(new datechooser.view.appearance.AppearancesList("Swing",
            new datechooser.view.appearance.ViewAppearance("custom",
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    true,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 255),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(128, 128, 128),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(255, 0, 0),
                    false,
                    false,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                (datechooser.view.BackRenderer)null,
                false,
                true)));
    dateChooserCombo4.setCalendarPreferredSize(new java.awt.Dimension(351, 242));
    dateChooserCombo4.setFieldFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
    profitPanel1.add(dateChooserCombo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 430, 310, 60));

    branch_lbl9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
    branch_lbl9.setText("Branch :");
    profitPanel1.add(branch_lbl9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 90, 60));

    profitsComboBox1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    profitsComboBox1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            profitsComboBox1ActionPerformed(evt);
        }
    });
    profitPanel1.add(profitsComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 210, 60));

    profitPanel.add(profitPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 540, 680));

    profitPanel2.setBackground(new java.awt.Color(255, 255, 255));
    profitPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    outLabel.setFont(new java.awt.Font("Segoe UI", 0, 100)); // NOI18N
    outLabel.setForeground(new java.awt.Color(113, 128, 147));
    outLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    outLabel.setText("000000");
    profitPanel2.add(outLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 280, 430, -1));

    jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/out.png"))); // NOI18N
    profitPanel2.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, -1, -1));

    yearLbl2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/year_blue.png"))); // NOI18N
    yearLbl2.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mousePressed(java.awt.event.MouseEvent evt) {
            yearLbl2MousePressed(evt);
        }
    });
    profitPanel2.add(yearLbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 520, -1, -1));

    monthLbl2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/month_blue.png"))); // NOI18N
    monthLbl2.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mousePressed(java.awt.event.MouseEvent evt) {
            monthLbl2MousePressed(evt);
        }
    });
    profitPanel2.add(monthLbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 520, -1, -1));

    dayLbl2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/day_blue.png"))); // NOI18N
    dayLbl2.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mousePressed(java.awt.event.MouseEvent evt) {
            dayLbl2MousePressed(evt);
        }
    });
    profitPanel2.add(dayLbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 520, -1, -1));

    inLabel7.setFont(new java.awt.Font("Segoe UI", 1, 60)); // NOI18N
    inLabel7.setForeground(new java.awt.Color(53, 59, 72));
    inLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    inLabel7.setText("Payments");
    profitPanel2.add(inLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 460, -1));

    profitPanel3.setBackground(new java.awt.Color(255, 255, 255));
    profitPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    outLabel1.setFont(new java.awt.Font("Segoe UI", 0, 100)); // NOI18N
    outLabel1.setForeground(new java.awt.Color(113, 128, 147));
    outLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    outLabel1.setText("000000");
    profitPanel3.add(outLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 520, -1));

    jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/out.png"))); // NOI18N
    profitPanel3.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, -1, -1));

    yearLbl4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/year_blue.png"))); // NOI18N
    yearLbl4.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            yearLbl4MouseClicked(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            yearLbl4MousePressed(evt);
        }
    });
    profitPanel3.add(yearLbl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 520, -1, -1));

    monthLbl4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/month_blue.png"))); // NOI18N
    monthLbl4.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mousePressed(java.awt.event.MouseEvent evt) {
            monthLbl4MousePressed(evt);
        }
    });
    profitPanel3.add(monthLbl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 520, -1, -1));

    dayLbl4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/day_blue.png"))); // NOI18N
    dayLbl4.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mousePressed(java.awt.event.MouseEvent evt) {
            dayLbl4MousePressed(evt);
        }
    });
    profitPanel3.add(dayLbl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 520, -1, -1));

    inLabel8.setFont(new java.awt.Font("Segoe UI", 1, 60)); // NOI18N
    inLabel8.setForeground(new java.awt.Color(53, 59, 72));
    inLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    inLabel8.setText("Payments");
    profitPanel3.add(inLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 460, -1));

    profitPanel2.add(profitPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 110, 600, 680));

    to_lbl1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
    to_lbl1.setForeground(new java.awt.Color(113, 128, 147));
    to_lbl1.setText("To :");
    profitPanel2.add(to_lbl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, 60, 60));

    jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
    jLabel9.setForeground(new java.awt.Color(255, 255, 255));
    jLabel9.setText("From :");
    profitPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 400, -1, 50));

    dateChooserCombo3.setCurrentView(new datechooser.view.appearance.AppearancesList("Swing",
        new datechooser.view.appearance.ViewAppearance("custom",
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.ButtonPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(0, 0, 255),
                true,
                true,
                new datechooser.view.appearance.swing.ButtonPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 255),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.ButtonPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(128, 128, 128),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.LabelPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.LabelPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(255, 0, 0),
                false,
                false,
                new datechooser.view.appearance.swing.ButtonPainter()),
            (datechooser.view.BackRenderer)null,
            false,
            true)));
dateChooserCombo3.setCalendarPreferredSize(new java.awt.Dimension(351, 242));
dateChooserCombo3.setFieldFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
profitPanel2.add(dateChooserCombo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 310, 60));

ok_lbl1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
ok_lbl1.setForeground(new java.awt.Color(204, 0, 0));
ok_lbl1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
ok_lbl1.setText("OK");
ok_lbl1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
ok_lbl1.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        ok_lbl1MouseClicked(evt);
    }
    });
    profitPanel2.add(ok_lbl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 430, 50, 40));

    profitPanel.add(profitPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 110, 540, 680));

    fullPanel.add(profitPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

    inventoryPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    inventoryPanel1.setBackground(new java.awt.Color(113, 128, 147));
    inventoryPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    dayLbl3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/day_white.png"))); // NOI18N
    dayLbl3.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mousePressed(java.awt.event.MouseEvent evt) {
            dayLbl3MousePressed(evt);
        }
    });
    inventoryPanel1.add(dayLbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 540, -1, -1));

    monthLbl3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/month_white.png"))); // NOI18N
    monthLbl3.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mousePressed(java.awt.event.MouseEvent evt) {
            monthLbl3MousePressed(evt);
        }
    });
    inventoryPanel1.add(monthLbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 540, -1, -1));

    yearLbl3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/year_white.png"))); // NOI18N
    yearLbl3.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mousePressed(java.awt.event.MouseEvent evt) {
            yearLbl3MousePressed(evt);
        }
    });
    inventoryPanel1.add(yearLbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 540, -1, -1));

    inLabel6.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
    inLabel6.setForeground(new java.awt.Color(53, 59, 72));
    inLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    inLabel6.setText("In");
    inventoryPanel1.add(inLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 325, 200, 65));

    inLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
    inLabel1.setForeground(new java.awt.Color(255, 255, 255));
    inLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    inLabel1.setText("000000");
    inventoryPanel1.add(inLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 190, 240, -1));

    inLabel2.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
    inLabel2.setForeground(new java.awt.Color(255, 255, 255));
    inLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    inLabel2.setText("000000");
    inventoryPanel1.add(inLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 385, 240, -1));

    jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/income.png"))); // NOI18N
    inventoryPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 270, -1, 60));

    jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/outcome.png"))); // NOI18N
    inventoryPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 80, -1, 60));

    inLabel5.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
    inLabel5.setForeground(new java.awt.Color(53, 59, 72));
    inLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    inLabel5.setText("Out");
    inventoryPanel1.add(inLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, 200, -1));

    return_inventory.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
    return_inventory.setForeground(new java.awt.Color(255, 255, 255));
    return_inventory.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    return_inventory.setText("000000");
    inventoryPanel1.add(return_inventory, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 240, 50));

    return_lbl.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
    return_lbl.setForeground(new java.awt.Color(53, 59, 72));
    return_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    return_lbl.setText("Returns");
    inventoryPanel1.add(return_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(-50, 130, 370, -1));

    jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/return.png"))); // NOI18N
    inventoryPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, -1, -1));

    inventoryComboBox.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    inventoryComboBox.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            inventoryComboBoxActionPerformed(evt);
        }
    });
    inventoryPanel1.add(inventoryComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 220, 50));

    branch_lbl10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
    branch_lbl10.setForeground(new java.awt.Color(53, 59, 72));
    branch_lbl10.setText("Branch :");
    inventoryPanel1.add(branch_lbl10, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 110, 50));

    ok_lbl2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
    ok_lbl2.setForeground(new java.awt.Color(204, 0, 0));
    ok_lbl2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    ok_lbl2.setText("OK");
    ok_lbl2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    ok_lbl2.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            ok_lbl2MouseClicked(evt);
        }
    });
    inventoryPanel1.add(ok_lbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 473, 50, -1));

    dateChooserCombo5.setCurrentView(new datechooser.view.appearance.AppearancesList("Swing",
        new datechooser.view.appearance.ViewAppearance("custom",
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.ButtonPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(0, 0, 255),
                true,
                true,
                new datechooser.view.appearance.swing.ButtonPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 255),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.ButtonPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(128, 128, 128),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.LabelPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.LabelPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(255, 0, 0),
                false,
                false,
                new datechooser.view.appearance.swing.ButtonPainter()),
            (datechooser.view.BackRenderer)null,
            false,
            true)));
dateChooserCombo5.setCalendarPreferredSize(new java.awt.Dimension(351, 242));
dateChooserCombo5.setFieldFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
inventoryPanel1.add(dateChooserCombo5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 470, 110, 40));

dateChooserCombo6.setCurrentView(new datechooser.view.appearance.AppearancesList("Swing",
    new datechooser.view.appearance.ViewAppearance("custom",
        new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
            new java.awt.Color(0, 0, 0),
            new java.awt.Color(0, 0, 255),
            false,
            true,
            new datechooser.view.appearance.swing.ButtonPainter()),
        new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
            new java.awt.Color(0, 0, 0),
            new java.awt.Color(0, 0, 255),
            true,
            true,
            new datechooser.view.appearance.swing.ButtonPainter()),
        new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
            new java.awt.Color(0, 0, 255),
            new java.awt.Color(0, 0, 255),
            false,
            true,
            new datechooser.view.appearance.swing.ButtonPainter()),
        new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
            new java.awt.Color(128, 128, 128),
            new java.awt.Color(0, 0, 255),
            false,
            true,
            new datechooser.view.appearance.swing.LabelPainter()),
        new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
            new java.awt.Color(0, 0, 0),
            new java.awt.Color(0, 0, 255),
            false,
            true,
            new datechooser.view.appearance.swing.LabelPainter()),
        new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
            new java.awt.Color(0, 0, 0),
            new java.awt.Color(255, 0, 0),
            false,
            false,
            new datechooser.view.appearance.swing.ButtonPainter()),
        (datechooser.view.BackRenderer)null,
        false,
        true)));
dateChooserCombo6.setCalendarPreferredSize(new java.awt.Dimension(351, 242));
dateChooserCombo6.setFieldFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
inventoryPanel1.add(dateChooserCombo6, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 470, 110, 40));

from_lbl2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
from_lbl2.setForeground(new java.awt.Color(255, 255, 255));
from_lbl2.setText("From :");
inventoryPanel1.add(from_lbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, -1, 60));

to_lbl2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
to_lbl2.setForeground(new java.awt.Color(255, 255, 255));
to_lbl2.setText("To :");
inventoryPanel1.add(to_lbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 460, 60, 60));

inventoryPanel.add(inventoryPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 530, 680));

inventoryPanel2.setBackground(new java.awt.Color(245, 246, 250));

jScrollPane16.setBackground(new java.awt.Color(255, 255, 255));
jScrollPane16.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

inventoryT.setAutoCreateRowSorter(true);
inventoryT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
inventoryT.setModel(new javax.swing.table.DefaultTableModel(
new Object [][] {

    },
    new String [] {

    }
    ));
    inventoryT.setGridColor(new java.awt.Color(153, 153, 153));
    inventoryT.setIntercellSpacing(new java.awt.Dimension(0, 0));
    inventoryT.setRowHeight(45);
    inventoryT.setShowHorizontalLines(true);
    inventoryT.setShowVerticalLines(false);
    jScrollPane16.setViewportView(inventoryT);

    jScrollPane17.setBackground(new java.awt.Color(255, 255, 255));
    jScrollPane17.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

    inventoryT1.setAutoCreateRowSorter(true);
    inventoryT1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    inventoryT1.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {

        }
    ));
    inventoryT1.setGridColor(new java.awt.Color(153, 153, 153));
    inventoryT1.setIntercellSpacing(new java.awt.Dimension(0, 0));
    inventoryT1.setRowHeight(45);
    inventoryT1.setShowHorizontalLines(true);
    inventoryT1.setShowVerticalLines(false);
    jScrollPane17.setViewportView(inventoryT1);

    outLabel9.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
    outLabel9.setForeground(new java.awt.Color(53, 59, 72));
    outLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    outLabel9.setText("Out");

    inLabel9.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
    inLabel9.setForeground(new java.awt.Color(53, 59, 72));
    inLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    inLabel9.setText("In");

    javax.swing.GroupLayout inventoryPanel2Layout = new javax.swing.GroupLayout(inventoryPanel2);
    inventoryPanel2.setLayout(inventoryPanel2Layout);
    inventoryPanel2Layout.setHorizontalGroup(
        inventoryPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(inventoryPanel2Layout.createSequentialGroup()
            .addGroup(inventoryPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.Alignment.LEADING))
            .addGap(0, 0, Short.MAX_VALUE))
        .addGroup(inventoryPanel2Layout.createSequentialGroup()
            .addGap(173, 173, 173)
            .addGroup(inventoryPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(outLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(inLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    inventoryPanel2Layout.setVerticalGroup(
        inventoryPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inventoryPanel2Layout.createSequentialGroup()
            .addComponent(outLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(inLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    inventoryPanel.add(inventoryPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, 550, 680));

    fullPanel.add(inventoryPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

    clientsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    clientsPanel1.setBackground(new java.awt.Color(113, 128, 147));
    clientsPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    itemPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    id_lbl11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
    id_lbl11.setText("ID :");
    itemPanel10.add(id_lbl11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, 50));

    jTextField61.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
    jTextField61.setEnabled(false);
    itemPanel10.add(jTextField61, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 290, 50));

    jTextField64.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
    jTextField64.addCaretListener(new javax.swing.event.CaretListener() {
        public void caretUpdate(javax.swing.event.CaretEvent evt) {
            jTextField64CaretUpdate(evt);
        }
    });
    itemPanel10.add(jTextField64, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 290, 50));

    name_lbl11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
    name_lbl11.setText("Client Name :");
    itemPanel10.add(name_lbl11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, 50));

    dept_lbl11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
    dept_lbl11.setText("Dept :");
    itemPanel10.add(dept_lbl11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 60, 50));

    jTextField70.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
    jTextField70.setForeground(new java.awt.Color(255, 51, 51));
    itemPanel10.add(jTextField70, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 290, 50));

    jTextField83.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
    itemPanel10.add(jTextField83, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, 290, 50));

    mob_lbl11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
    mob_lbl11.setText("Mobile :");
    itemPanel10.add(mob_lbl11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, 50));

    clientsPanel1.add(itemPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 510, 280));

    view_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewD.png"))); // NOI18N
    view_icon11.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            view_icon11MouseClicked(evt);
        }
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            view_icon11MouseEntered(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            view_icon11MouseExited(evt);
        }
    });
    clientsPanel1.add(view_icon11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 520, 50, 60));

    view_lbl11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
    view_lbl11.setForeground(new java.awt.Color(30, 34, 43));
    view_lbl11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    view_lbl11.setText("View");
    clientsPanel1.add(view_lbl11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 580, 50, 30));

    add_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addD.png"))); // NOI18N
    add_icon11.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            add_icon11MouseClicked(evt);
        }
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            add_icon11MouseEntered(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            add_icon11MouseExited(evt);
        }
    });
    clientsPanel1.add(add_icon11, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 520, 50, 60));

    add_lbl11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
    add_lbl11.setForeground(new java.awt.Color(30, 34, 43));
    add_lbl11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    add_lbl11.setText("Add");
    clientsPanel1.add(add_lbl11, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 580, 60, 30));

    delete_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteD.png"))); // NOI18N
    delete_icon11.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            delete_icon11MouseClicked(evt);
        }
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            delete_icon11MouseEntered(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            delete_icon11MouseExited(evt);
        }
    });
    clientsPanel1.add(delete_icon11, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 520, 50, 60));

    del_lbl11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
    del_lbl11.setForeground(new java.awt.Color(30, 34, 43));
    del_lbl11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    del_lbl11.setText("Delete");
    clientsPanel1.add(del_lbl11, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 580, 70, 30));

    edit_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editD.png"))); // NOI18N
    edit_icon11.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            edit_icon11MouseClicked(evt);
        }
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            edit_icon11MouseEntered(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            edit_icon11MouseExited(evt);
        }
    });
    clientsPanel1.add(edit_icon11, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 520, 50, 60));

    edit_lbl11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
    edit_lbl11.setForeground(new java.awt.Color(30, 34, 43));
    edit_lbl11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    edit_lbl11.setText("Edit");
    clientsPanel1.add(edit_lbl11, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 580, 50, 30));

    clear_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearD.png"))); // NOI18N
    clear_icon11.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            clear_icon11MouseClicked(evt);
        }
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            clear_icon11MouseEntered(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            clear_icon11MouseExited(evt);
        }
    });
    clientsPanel1.add(clear_icon11, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 520, 50, 60));

    search_lbl11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
    search_lbl11.setForeground(new java.awt.Color(30, 34, 43));
    search_lbl11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    search_lbl11.setText("Clear");
    clientsPanel1.add(search_lbl11, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 580, 70, 30));

    clientsPanel.add(clientsPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 530, 680));

    clientsPanel2.setBackground(new java.awt.Color(245, 246, 250));

    jScrollPane6.setBackground(new java.awt.Color(255, 255, 255));
    jScrollPane6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

    clientT.setAutoCreateRowSorter(true);
    clientT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    clientT.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {

        }
    ));
    clientT.setGridColor(new java.awt.Color(153, 153, 153));
    clientT.setIntercellSpacing(new java.awt.Dimension(0, 0));
    clientT.setRowHeight(45);
    clientT.setShowHorizontalLines(true);
    clientT.setShowVerticalLines(false);
    clientT.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            clientTMouseClicked(evt);
        }
    });
    jScrollPane6.setViewportView(clientT);

    javax.swing.GroupLayout clientsPanel2Layout = new javax.swing.GroupLayout(clientsPanel2);
    clientsPanel2.setLayout(clientsPanel2Layout);
    clientsPanel2Layout.setHorizontalGroup(
        clientsPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(clientsPanel2Layout.createSequentialGroup()
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
    );
    clientsPanel2Layout.setVerticalGroup(
        clientsPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
    );

    clientsPanel.add(clientsPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, 550, 680));

    fullPanel.add(clientsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

    usersPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    usersPanel4.setBackground(new java.awt.Color(113, 128, 147));
    usersPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    itemPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    password.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
    password.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            passwordActionPerformed(evt);
        }
    });
    itemPanel11.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 290, 40));

    pass_lbl12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
    pass_lbl12.setText("Password :");
    itemPanel11.add(pass_lbl12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, 40));

    userName.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
    userName.addCaretListener(new javax.swing.event.CaretListener() {
        public void caretUpdate(javax.swing.event.CaretEvent evt) {
            userNameCaretUpdate(evt);
        }
    });
    itemPanel11.add(userName, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 290, 40));

    user_lbl12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
    user_lbl12.setText("User Name :");
    itemPanel11.add(user_lbl12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, 40));

    type_lbl12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
    type_lbl12.setText("Type :");
    itemPanel11.add(type_lbl12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, 50));
    itemPanel11.add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 180, -1, -1));

    programHours.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
    programHours.setEnabled(false);
    itemPanel11.add(programHours, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, 290, 40));

    hours_lbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
    hours_lbl.setText("Hours :");
    itemPanel11.add(hours_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, -1, 40));

    add_hours.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
    add_hours.setForeground(new java.awt.Color(30, 34, 43));
    add_hours.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    add_hours.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/plus.png"))); // NOI18N
    add_hours.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            add_hoursMouseClicked(evt);
        }
    });
    itemPanel11.add(add_hours, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 250, 40, 40));

    type.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
    type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Type : ", "User", "Admin" }));
    itemPanel11.add(type, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 290, 50));
    itemPanel11.add(user7, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 350, -1, -1));
    itemPanel11.add(user8, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 390, -1, -1));
    itemPanel11.add(user9, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 430, -1, -1));
    itemPanel11.add(user10, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 470, -1, -1));
    itemPanel11.add(user3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, -1, -1));
    itemPanel11.add(user4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 430, -1, -1));
    itemPanel11.add(user5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 470, -1, -1));
    itemPanel11.add(user6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 310, -1, -1));
    itemPanel11.add(user2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 350, -1, -1));
    itemPanel11.add(user1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, -1, -1));

    check_lbl1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    check_lbl1.setText("Cons");
    itemPanel11.add(check_lbl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 470, -1, -1));

    check_lbl2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    check_lbl2.setText("Stock");
    itemPanel11.add(check_lbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 310, -1, -1));

    check_lbl3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    check_lbl3.setText("Sales");
    itemPanel11.add(check_lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 350, -1, -1));

    check_lbl4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    check_lbl4.setText("Purchases");
    itemPanel11.add(check_lbl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 390, -1, -1));

    check_lbl5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    check_lbl5.setText("Depts");
    itemPanel11.add(check_lbl5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 430, -1, -1));

    check_lbl6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    check_lbl6.setText("Clients");
    itemPanel11.add(check_lbl6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 470, 60, -1));

    check_lbl7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    check_lbl7.setText("Inventory");
    itemPanel11.add(check_lbl7, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 430, -1, -1));

    check_lbl8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    check_lbl8.setText("Profits");
    itemPanel11.add(check_lbl8, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 390, -1, -1));

    check_lbl9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    check_lbl9.setText("Orders");
    itemPanel11.add(check_lbl9, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 350, -1, -1));

    check_lbl10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    check_lbl10.setText("Returns");
    itemPanel11.add(check_lbl10, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 310, -1, -1));

    branch.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
    itemPanel11.add(branch, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 290, 50));

    branch_lbl12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
    branch_lbl12.setText("Branch :");
    itemPanel11.add(branch_lbl12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, -1, 40));

    add_branches.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
    add_branches.setForeground(new java.awt.Color(30, 34, 43));
    add_branches.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    add_branches.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/plus.png"))); // NOI18N
    add_branches.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            add_branchesMouseClicked(evt);
        }
    });
    itemPanel11.add(add_branches, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 190, 40, 50));

    usersPanel4.add(itemPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 510, 510));

    view_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewD.png"))); // NOI18N
    view_icon12.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            view_icon12MouseClicked(evt);
        }
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            view_icon12MouseEntered(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            view_icon12MouseExited(evt);
        }
    });
    usersPanel4.add(view_icon12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 540, 50, 60));

    view_lbl12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
    view_lbl12.setForeground(new java.awt.Color(30, 34, 43));
    view_lbl12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    view_lbl12.setText("View");
    usersPanel4.add(view_lbl12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 600, 50, 30));

    add_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addD.png"))); // NOI18N
    add_icon12.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            add_icon12MouseClicked(evt);
        }
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            add_icon12MouseEntered(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            add_icon12MouseExited(evt);
        }
    });
    usersPanel4.add(add_icon12, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 540, 50, 60));

    delete_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteD.png"))); // NOI18N
    delete_icon12.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            delete_icon12MouseClicked(evt);
        }
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            delete_icon12MouseEntered(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            delete_icon12MouseExited(evt);
        }
    });
    usersPanel4.add(delete_icon12, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 540, 50, 60));

    del_lbl12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
    del_lbl12.setForeground(new java.awt.Color(30, 34, 43));
    del_lbl12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    del_lbl12.setText("Delete");
    usersPanel4.add(del_lbl12, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 600, 70, 30));

    edit_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editD.png"))); // NOI18N
    edit_icon12.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            edit_icon12MouseClicked(evt);
        }
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            edit_icon12MouseEntered(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            edit_icon12MouseExited(evt);
        }
    });
    usersPanel4.add(edit_icon12, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 540, 50, 60));

    edit_lbl12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
    edit_lbl12.setForeground(new java.awt.Color(30, 34, 43));
    edit_lbl12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    edit_lbl12.setText("Edit");
    usersPanel4.add(edit_lbl12, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 600, 70, 30));

    clear_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearD.png"))); // NOI18N
    clear_icon12.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            clear_icon12MouseClicked(evt);
        }
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            clear_icon12MouseEntered(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            clear_icon12MouseExited(evt);
        }
    });
    usersPanel4.add(clear_icon12, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 540, 50, 60));

    search_lbl12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
    search_lbl12.setForeground(new java.awt.Color(30, 34, 43));
    search_lbl12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    search_lbl12.setText("Clear");
    usersPanel4.add(search_lbl12, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 600, 70, 30));

    add_lbl13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
    add_lbl13.setForeground(new java.awt.Color(30, 34, 43));
    add_lbl13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    add_lbl13.setText("Add");
    usersPanel4.add(add_lbl13, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 600, 60, 30));

    usersPanel.add(usersPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 530, 680));

    usersPanel5.setBackground(new java.awt.Color(245, 246, 250));

    jScrollPane7.setBackground(new java.awt.Color(255, 255, 255));
    jScrollPane7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

    userT.setAutoCreateRowSorter(true);
    userT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    userT.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {

        }
    ));
    userT.setGridColor(new java.awt.Color(153, 153, 153));
    userT.setIntercellSpacing(new java.awt.Dimension(0, 0));
    userT.setRowHeight(45);
    userT.setShowHorizontalLines(true);
    userT.setShowVerticalLines(false);
    userT.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            userTMouseClicked(evt);
        }
    });
    jScrollPane7.setViewportView(userT);

    javax.swing.GroupLayout usersPanel5Layout = new javax.swing.GroupLayout(usersPanel5);
    usersPanel5.setLayout(usersPanel5Layout);
    usersPanel5Layout.setHorizontalGroup(
        usersPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(usersPanel5Layout.createSequentialGroup()
            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 1, Short.MAX_VALUE))
    );
    usersPanel5Layout.setVerticalGroup(
        usersPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
    );

    usersPanel.add(usersPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, 550, 680));

    fullPanel.add(usersPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

    helpPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    helpPanel1.setBackground(new java.awt.Color(113, 128, 147));
    helpPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jLabel63.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
    jLabel63.setForeground(new java.awt.Color(255, 255, 255));
    jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel63.setText("How to add user ?");
    helpPanel1.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 690, -1));

    videoPlay1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/play_small.png"))); // NOI18N
    videoPlay1.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            videoPlay1MouseClicked(evt);
        }
    });
    helpPanel1.add(videoPlay1, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 60, -1, -1));

    helpPanel.add(helpPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 1080, 680));

    helpPanel2.setBackground(new java.awt.Color(245, 246, 250));

    javax.swing.GroupLayout helpPanel2Layout = new javax.swing.GroupLayout(helpPanel2);
    helpPanel2.setLayout(helpPanel2Layout);
    helpPanel2Layout.setHorizontalGroup(
        helpPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 550, Short.MAX_VALUE)
    );
    helpPanel2Layout.setVerticalGroup(
        helpPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 680, Short.MAX_VALUE)
    );

    helpPanel.add(helpPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, 550, 680));

    fullPanel.add(helpPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

    aboutPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    aboutPanel1.setBackground(new java.awt.Color(255, 255, 255));
    aboutPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jLabel84.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo.png"))); // NOI18N
    aboutPanel1.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 120, 420, -1));

    jLabel85.setFont(new java.awt.Font("Segoe UI", 1, 100)); // NOI18N
    jLabel85.setForeground(new java.awt.Color(53, 59, 72));
    jLabel85.setText("Hash Store");
    aboutPanel1.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, -1, 120));

    jLabel94.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
    jLabel94.setForeground(new java.awt.Color(53, 59, 72));
    jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel94.setText("Email : hash.softwarecompany@gmail.com                   Mobile : (+2) 01068322486");
    aboutPanel1.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 580, -1, -1));

    jLabel95.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
    jLabel95.setForeground(new java.awt.Color(53, 59, 72));
    jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel95.setText("All Rights reserved 2019    © Hash Company ©     جميع الحقوق محفوظة");
    aboutPanel1.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 530, -1, -1));

    aboutPanel.add(aboutPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 1080, 680));

    fullPanel.add(aboutPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

    getContentPane().add(fullPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1240, 790));

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pane1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane1MousePressed
        // TODO add your handling code here:
        lang_lbl.setVisible(true);
        pdf_lbl.setVisible(false);
        print_lbl.setVisible(false);
        numbers();
        setColor(pane1, label1);
        resetColor(new JPanel[]{pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14});
        showPanel(homePanel);
        hidePanel(new JPanel[]{stockPanel, salesPanel, paymentPanel, deptPanel, ConsPanel, returnPanel, orderPanel, profitPanel, inventoryPanel, clientsPanel, usersPanel, helpPanel, aboutPanel});
        panalIs = 1;
    }//GEN-LAST:event_pane1MousePressed

    private void pane2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane2MousePressed

        if (type_lbl1.getText().equals("Super")) {
            lang_lbl.setVisible(false);
            pdf_lbl.setVisible(true);
            print_lbl.setVisible(false);
            setColor(pane2, label2);
            resetColor(new JPanel[]{pane1, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14});
            showPanel(stockPanel);
            hidePanel(new JPanel[]{homePanel, salesPanel, paymentPanel, deptPanel, ConsPanel, returnPanel, orderPanel, profitPanel, inventoryPanel, clientsPanel, usersPanel, helpPanel, aboutPanel});
            panalIs = 2;

            viewStock();
        } else {
            String sql = "SELECT * FROM users WHERE User_name = '" + user_lbl.getText() + "';";
            DBcon db = new DBcon();
            try {
                db.rs = db.st.executeQuery(sql);
                while (db.rs.next()) {
                    if (db.rs.getBoolean("1")) {
                        lang_lbl.setVisible(false);
                        pdf_lbl.setVisible(true);
                        print_lbl.setVisible(false);
                        setColor(pane2, label2);
                        resetColor(new JPanel[]{pane1, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14});
                        showPanel(stockPanel);
                        hidePanel(new JPanel[]{homePanel, salesPanel, paymentPanel, deptPanel, ConsPanel, returnPanel, orderPanel, profitPanel, inventoryPanel, clientsPanel, usersPanel, helpPanel, aboutPanel});
                        viewStock();
                        panalIs = 2;
                    } else {
                        pane2.setBackground(new Color(232, 65, 24));
                        label2.setForeground(new Color(255, 255, 255));

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(Home.class
                        .getName()).log(Level.SEVERE, null, ex);

            }
        }


    }//GEN-LAST:event_pane2MousePressed

    private void pane3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane3MousePressed

        if (type_lbl1.getText().equals("Super")) {
            lang_lbl.setVisible(false);
            pdf_lbl.setVisible(true);
            print_lbl.setVisible(true);
            setColor(pane3, label3);
            resetColor(new JPanel[]{pane1, pane2, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14});
            showPanel(salesPanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, paymentPanel, deptPanel, ConsPanel, returnPanel, orderPanel, profitPanel, inventoryPanel, clientsPanel, usersPanel, helpPanel, aboutPanel});
            panalIs = 3;

            view_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));

            viewSales();
        } else {
            String sql = "SELECT * FROM users WHERE User_name = '" + user_lbl.getText() + "';";
            DBcon db = new DBcon();
            try {

                db.rs = db.st.executeQuery(sql);

                while (db.rs.next()) {

                    if (db.rs.getBoolean("2")) {

                        lang_lbl.setVisible(false);
                        pdf_lbl.setVisible(true);
                        print_lbl.setVisible(true);
                        setColor(pane3, label3);
                        resetColor(new JPanel[]{pane1, pane2, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14});
                        showPanel(salesPanel);
                        hidePanel(new JPanel[]{homePanel, stockPanel, paymentPanel, deptPanel, ConsPanel, returnPanel, orderPanel, profitPanel, inventoryPanel, clientsPanel, usersPanel, helpPanel, aboutPanel});
                        panalIs = 3;
                        view_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));

                        viewSales();

                    } else {
                        pane3.setBackground(new Color(232, 65, 24));
                        label3.setForeground(new Color(255, 255, 255));

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(Home.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_pane3MousePressed

    private void pane4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane4MousePressed

        if (type_lbl1.getText().equals("Super")) {
            lang_lbl.setVisible(false);
            pdf_lbl.setVisible(true);
            print_lbl.setVisible(true);
            setColor(pane4, label4);
            resetColor(new JPanel[]{pane1, pane2, pane3, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14});
            showPanel(paymentPanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, salesPanel, deptPanel, ConsPanel, returnPanel, orderPanel, profitPanel, inventoryPanel, clientsPanel, usersPanel, helpPanel, aboutPanel});
            panalIs = 4;
            view_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));

            viewPayment();
        } else {
            String sql = "SELECT * FROM users WHERE User_name = '" + user_lbl.getText() + "';";
            DBcon db = new DBcon();
            try {

                db.rs = db.st.executeQuery(sql);

                while (db.rs.next()) {

                    if (db.rs.getBoolean("3")) {

                        lang_lbl.setVisible(false);
                        pdf_lbl.setVisible(true);
                        print_lbl.setVisible(true);
                        setColor(pane4, label4);
                        resetColor(new JPanel[]{pane1, pane2, pane3, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14});
                        showPanel(paymentPanel);
                        hidePanel(new JPanel[]{homePanel, stockPanel, salesPanel, deptPanel, ConsPanel, returnPanel, orderPanel, profitPanel, inventoryPanel, clientsPanel, usersPanel, helpPanel, aboutPanel});
                        panalIs = 4;

                        view_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));

                        viewPayment();
                    } else {
                        pane4.setBackground(new Color(232, 65, 24));
                        label4.setForeground(new Color(255, 255, 255));

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(Home.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_pane4MousePressed

    private void pane5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane5MousePressed

        if (type_lbl1.getText().equals("Super")) {
            lang_lbl.setVisible(false);
            pdf_lbl.setVisible(true);
            print_lbl.setVisible(false);
            setColor(pane5, label5);
            resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label6, label7, label8, label9, label10, label11, label12, label13, label14});
            showPanel(deptPanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, salesPanel, paymentPanel, ConsPanel, returnPanel, orderPanel, profitPanel, inventoryPanel, clientsPanel, usersPanel, helpPanel, aboutPanel});
            panalIs = 5;
        }
        String sql = "SELECT * FROM users WHERE User_name = '" + user_lbl.getText() + "';";
        DBcon db = new DBcon();
        try {

            db.rs = db.st.executeQuery(sql);

            while (db.rs.next()) {

                if (db.rs.getBoolean("4")) {
                    lang_lbl.setVisible(false);
                    pdf_lbl.setVisible(true);
                    print_lbl.setVisible(false);
                    setColor(pane5, label5);
                    resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label6, label7, label8, label9, label10, label11, label12, label13, label14});
                    showPanel(deptPanel);
                    hidePanel(new JPanel[]{homePanel, stockPanel, salesPanel, paymentPanel, ConsPanel, returnPanel, orderPanel, profitPanel, inventoryPanel, clientsPanel, usersPanel, helpPanel, aboutPanel});
                    panalIs = 5;
                } else {
                    pane5.setBackground(new Color(232, 65, 24));
                    label5.setForeground(new Color(255, 255, 255));

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Home.class
                    .getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_pane5MousePressed

    private void pane6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane6MousePressed
        if (type_lbl1.getText().equals("Super")) {
            lang_lbl.setVisible(false);
            pdf_lbl.setVisible(true);
            print_lbl.setVisible(false);
            setColor(pane6, label6);
            resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane7, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label7, label8, label9, label10, label11, label12, label13, label14});
            showPanel(ConsPanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, salesPanel, paymentPanel, deptPanel, returnPanel, orderPanel, profitPanel, inventoryPanel, clientsPanel, usersPanel, helpPanel, aboutPanel});
            panalIs = 6;
            view_icon6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));

            //view cons
            viewCons();

        } else {
            String sql = "SELECT * FROM users WHERE User_name = '" + user_lbl.getText() + "';";
            DBcon db = new DBcon();
            try {

                db.rs = db.st.executeQuery(sql);

                while (db.rs.next()) {

                    if (db.rs.getBoolean("5")) {
                        lang_lbl.setVisible(false);
                        pdf_lbl.setVisible(true);
                        print_lbl.setVisible(false);
                        setColor(pane6, label6);
                        resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane7, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label7, label8, label9, label10, label11, label12, label13, label14});
                        showPanel(ConsPanel);
                        hidePanel(new JPanel[]{homePanel, stockPanel, salesPanel, paymentPanel, deptPanel, returnPanel, orderPanel, profitPanel, inventoryPanel, clientsPanel, usersPanel, helpPanel, aboutPanel});
                        panalIs = 6;
                        view_icon6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));

                        //view cons
                        viewCons();
                    } else {
                        pane6.setBackground(new Color(232, 65, 24));
                        label6.setForeground(new Color(255, 255, 255));

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(Home.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }


    }//GEN-LAST:event_pane6MousePressed

    private void pane7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane7MousePressed
        if (type_lbl1.getText().equals("Super")) {
            lang_lbl.setVisible(false);
            pdf_lbl.setVisible(true);
            print_lbl.setVisible(false);
            setColor(pane7, label7);
            resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label6, label8, label9, label10, label11, label12, label13, label14});
            showPanel(returnPanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, salesPanel, paymentPanel, deptPanel, ConsPanel, orderPanel, profitPanel, inventoryPanel, clientsPanel, usersPanel, helpPanel, aboutPanel});
            panalIs = 7;

            view_icon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));
            // view returns

            viewReturns();
        } else {
            String sql = "SELECT * FROM users WHERE User_name = '" + user_lbl.getText() + "';";
            DBcon dbd = new DBcon();
            try {

                dbd.rs = dbd.st.executeQuery(sql);

                while (dbd.rs.next()) {

                    if (dbd.rs.getBoolean("6")) {
                        lang_lbl.setVisible(false);
                        pdf_lbl.setVisible(true);
                        print_lbl.setVisible(false);
                        setColor(pane7, label7);
                        resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label6, label8, label9, label10, label11, label12, label13, label14});
                        showPanel(returnPanel);
                        hidePanel(new JPanel[]{homePanel, stockPanel, salesPanel, paymentPanel, deptPanel, ConsPanel, orderPanel, profitPanel, inventoryPanel, clientsPanel, usersPanel, helpPanel, aboutPanel});
                        panalIs = 7;

                        view_icon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));
                        // view returns

                        viewReturns();
                    } else {
                        pane7.setBackground(new Color(232, 65, 24));
                        label7.setForeground(new Color(255, 255, 255));

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(Home.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_pane7MousePressed

    private void pane8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane8MousePressed
        if (type_lbl1.getText().equals("Super")) {
            lang_lbl.setVisible(false);
            pdf_lbl.setVisible(true);
            print_lbl.setVisible(true);
            setColor(pane8, label8);
            resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label6, label7, label9, label10, label11, label12, label13, label14});
            showPanel(orderPanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, salesPanel, paymentPanel, deptPanel, ConsPanel, returnPanel, profitPanel, inventoryPanel, clientsPanel, usersPanel, helpPanel, aboutPanel});
            panalIs = 8;

            view_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));

            viewOrders();
        } else {
            String sql = "SELECT * FROM users WHERE User_name = '" + user_lbl.getText() + "';";
            DBcon db = new DBcon();
            try {

                db.rs = db.st.executeQuery(sql);

                while (db.rs.next()) {

                    if (db.rs.getBoolean("7")) {
                        lang_lbl.setVisible(false);
                        pdf_lbl.setVisible(true);
                        print_lbl.setVisible(false);
                        setColor(pane8, label8);
                        resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label6, label7, label9, label10, label11, label12, label13, label14});
                        showPanel(orderPanel);
                        hidePanel(new JPanel[]{homePanel, stockPanel, salesPanel, paymentPanel, deptPanel, ConsPanel, returnPanel, profitPanel, inventoryPanel, clientsPanel, usersPanel, helpPanel, aboutPanel});
                        panalIs = 8;

                        view_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));

                        viewOrders();
                    } else {
                        pane8.setBackground(new Color(232, 65, 24));
                        label8.setForeground(new Color(255, 255, 255));

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(Home.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_pane8MousePressed

    private void pane9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane9MousePressed
        if (type_lbl1.getText().equals("Super")) {
            lang_lbl.setVisible(false);
            pdf_lbl.setVisible(false);
            print_lbl.setVisible(false);
            decideImgLbl.setVisible(false);
            setColor(pane9, label9);
            resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label6, label7, label8, label10, label11, label12, label13, label14});
            showPanel(profitPanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, salesPanel, paymentPanel, deptPanel, ConsPanel, returnPanel, orderPanel, inventoryPanel, clientsPanel, usersPanel, helpPanel, aboutPanel});

            panalIs = 9;
        } else {
            String sql = "SELECT * FROM users WHERE User_name = '" + user_lbl.getText() + "';";
            DBcon db = new DBcon();
            try {

                db.rs = db.st.executeQuery(sql);

                while (db.rs.next()) {

                    if (db.rs.getBoolean("8")) {
                        lang_lbl.setVisible(false);
                        pdf_lbl.setVisible(false);
                        print_lbl.setVisible(false);
                        decideImgLbl.setVisible(false);
                        setColor(pane9, label9);
                        resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label6, label7, label8, label10, label11, label12, label13, label14});
                        showPanel(profitPanel);
                        hidePanel(new JPanel[]{homePanel, stockPanel, salesPanel, paymentPanel, deptPanel, ConsPanel, returnPanel, orderPanel, inventoryPanel, clientsPanel, usersPanel, helpPanel, aboutPanel});

                        panalIs = 9;
                    } else {
                        pane9.setBackground(new Color(232, 65, 24));
                        label9.setForeground(new Color(255, 255, 255));

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(Home.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_pane9MousePressed

    private void pane10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane10MousePressed
        if (type_lbl1.getText().equals("Super")) {
            lang_lbl.setVisible(false);
            pdf_lbl.setVisible(true);
            print_lbl.setVisible(false);
            setColor(pane10, label10);
            resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label6, label7, label8, label9, label11, label12, label13, label14});
            showPanel(inventoryPanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, salesPanel, paymentPanel, deptPanel, ConsPanel, returnPanel, orderPanel, profitPanel, clientsPanel, usersPanel, helpPanel, aboutPanel});
            panalIs = 10;
        } else {
            String sql = "SELECT * FROM users WHERE User_name = '" + user_lbl.getText() + "';";
            DBcon db = new DBcon();
            try {

                db.rs = db.st.executeQuery(sql);

                while (db.rs.next()) {

                    if (db.rs.getBoolean("9")) {
                        lang_lbl.setVisible(false);
                        pdf_lbl.setVisible(true);
                        print_lbl.setVisible(false);
                        setColor(pane10, label10);
                        resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label6, label7, label8, label9, label11, label12, label13, label14});
                        showPanel(inventoryPanel);
                        hidePanel(new JPanel[]{homePanel, stockPanel, salesPanel, paymentPanel, deptPanel, ConsPanel, returnPanel, orderPanel, profitPanel, clientsPanel, usersPanel, helpPanel, aboutPanel});
                        panalIs = 10;
                    } else {
                        pane10.setBackground(new Color(232, 65, 24));
                        label10.setForeground(new Color(255, 255, 255));

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(Home.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_pane10MousePressed

    private void pane11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane11MousePressed
        if (type_lbl1.getText().equals("Super")) {
            lang_lbl.setVisible(false);
            pdf_lbl.setVisible(true);
            print_lbl.setVisible(false);
            setColor(pane11, label11);
            resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label12, label13, label14});
            showPanel(clientsPanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, salesPanel, paymentPanel, deptPanel, ConsPanel, returnPanel, orderPanel, profitPanel, inventoryPanel, usersPanel, helpPanel, aboutPanel});
            panalIs = 11;

            //view clients
            view_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));

            viewClients();

        } else {
            String sql = "SELECT * FROM users WHERE User_name = '" + user_lbl.getText() + "';";

            DBcon db = new DBcon();
            try {

                db.rs = db.st.executeQuery(sql);

                while (db.rs.next()) {

                    if (db.rs.getBoolean("10")) {
                        lang_lbl.setVisible(false);
                        pdf_lbl.setVisible(true);
                        print_lbl.setVisible(false);
                        setColor(pane11, label11);
                        resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label12, label13, label14});
                        showPanel(clientsPanel);
                        hidePanel(new JPanel[]{homePanel, stockPanel, salesPanel, paymentPanel, deptPanel, ConsPanel, returnPanel, orderPanel, profitPanel, inventoryPanel, usersPanel, helpPanel, aboutPanel});
                        panalIs = 11;

                        //view clients
                        view_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));

                        viewClients();

                    } else {
                        pane11.setBackground(new Color(232, 65, 24));
                        label11.setForeground(new Color(255, 255, 255));

                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Home.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_pane11MousePressed

    private void pane12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane12MousePressed
        add_hours.setVisible(false);
        programHours.setEnabled(false);
        if (type_lbl1.getText().equals("Super")) {
            add_hours.setVisible(true);
            programHours.setEnabled(true);
        }
        if (type_lbl1.getText().equals("Super") || type_lbl1.getText().equals("Admin")) {
            lang_lbl.setVisible(false);
            pdf_lbl.setVisible(true);
            print_lbl.setVisible(false);
            setColor(pane12, label12);
            resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label13, label14});
            showPanel(usersPanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, salesPanel, paymentPanel, deptPanel, ConsPanel, returnPanel, orderPanel, profitPanel, inventoryPanel, clientsPanel, helpPanel, aboutPanel});
            panalIs = 12;

            view_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));

            // view user
            viewUsers();
        } else {
            pane12.setBackground(new Color(232, 65, 24));
            label12.setForeground(new Color(255, 255, 255));
        }

    }//GEN-LAST:event_pane12MousePressed

    private void pane13MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane13MousePressed
        lang_lbl.setVisible(false);
        pdf_lbl.setVisible(false);
        print_lbl.setVisible(false);
        setColor(pane13, label13);
        resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label14});
        showPanel(helpPanel);
        hidePanel(new JPanel[]{homePanel, stockPanel, salesPanel, paymentPanel, deptPanel, ConsPanel, returnPanel, orderPanel, profitPanel, inventoryPanel, clientsPanel, usersPanel, aboutPanel});
        panalIs = 13;
    }//GEN-LAST:event_pane13MousePressed

    private void pane14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane14MousePressed
        lang_lbl.setVisible(false);
        pdf_lbl.setVisible(false);
        print_lbl.setVisible(false);
        setColor(pane14, label14);
        resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane13}, new JLabel[]{label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13});
        showPanel(aboutPanel);
        hidePanel(new JPanel[]{homePanel, stockPanel, salesPanel, paymentPanel, deptPanel, ConsPanel, returnPanel, orderPanel, profitPanel, inventoryPanel, clientsPanel, usersPanel, helpPanel});
        panalIs = 14;
    }//GEN-LAST:event_pane14MousePressed

    int posX, posY;

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        posX = evt.getX();
        posY = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        if (maximized == true) {
            int x = evt.getXOnScreen();
            int y = evt.getYOnScreen();
            this.setLocation(x - posX, y - posY);
        }
    }//GEN-LAST:event_formMouseDragged

    private void jLabel4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MousePressed
//        timeElapsed();
        System.exit(0);
    }//GEN-LAST:event_jLabel4MousePressed

    private void jLabel18MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MousePressed
        if (maximized) {
            Home.this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Home.this.setMaximizedBounds(env.getMaximumWindowBounds());
            maximized = false;
        } else {
            setExtendedState(JFrame.NORMAL);
            maximized = true;
        }
    }//GEN-LAST:event_jLabel18MousePressed

    private void salestotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salestotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_salestotalActionPerformed

    private void salesRestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salesRestActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_salesRestActionPerformed

    private void orderTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_orderTotalActionPerformed

    private void orderRestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderRestActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_orderRestActionPerformed

    private void eyeLabel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eyeLabel5MouseEntered
        eyeLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye1.png")));
    }//GEN-LAST:event_eyeLabel5MouseEntered

    private void eyeLabel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eyeLabel5MouseExited
        eyeLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye2.png")));
    }//GEN-LAST:event_eyeLabel5MouseExited

    private void eyeLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eyeLabel1MouseEntered
        eyeLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye1.png")));
    }//GEN-LAST:event_eyeLabel1MouseEntered

    private void eyeLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eyeLabel1MouseExited
        eyeLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye2.png")));
    }//GEN-LAST:event_eyeLabel1MouseExited

    private void eyeLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eyeLabel2MouseEntered
        eyeLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye1.png")));
    }//GEN-LAST:event_eyeLabel2MouseEntered

    private void eyeLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eyeLabel2MouseExited
        eyeLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye2.png")));
    }//GEN-LAST:event_eyeLabel2MouseExited

    private void eyeLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eyeLabel3MouseEntered
        eyeLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye1.png")));
    }//GEN-LAST:event_eyeLabel3MouseEntered

    private void eyeLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eyeLabel3MouseExited
        eyeLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye2.png")));
    }//GEN-LAST:event_eyeLabel3MouseExited

    private void eyeLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eyeLabel4MouseEntered
        eyeLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye1.png")));
    }//GEN-LAST:event_eyeLabel4MouseEntered

    private void eyeLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eyeLabel4MouseExited
        eyeLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye2.png")));
    }//GEN-LAST:event_eyeLabel4MouseExited

    private void eyeLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eyeLabel6MouseEntered
        eyeLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye1.png")));
    }//GEN-LAST:event_eyeLabel6MouseEntered

    private void eyeLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eyeLabel6MouseExited
        eyeLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye2.png")));
    }//GEN-LAST:event_eyeLabel6MouseExited

    private void jLabel19MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MousePressed
        new login().setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel19MousePressed

    private void dayLbl1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dayLbl1MousePressed
        setLabelIcon(dayLbl1, "/images/day_dark.png");
        setLabelIcon(monthLbl1, "/images/month_white.png");
        setLabelIcon(yearLbl1, "/images/year_white.png");

        //profits in day       
        DBcon d = new DBcon();
        DBcon db = new DBcon();
        String branch = String.valueOf(profitsComboBox1.getSelectedItem());
        float temp = 0;
        float temp1 = 0;
        String stock_id = "";
        String sql = null;
        String sql1;
        String sql2;

        try {
            int branchId = 0;
            d.rs = d.st.executeQuery("select * from branch WHERE name = '" + branch + "';");
            while ((d.rs).next()) {
                branchId = d.rs.getInt("id");
            }
            if (branch.equals("all")) {
                sql1 = "SELECT * FROM `returns` WHERE date = '" + LocalDate.now() + "';";
                sql2 = "SELECT * FROM `sales` WHERE  date = '" + LocalDate.now() + "'";
            } else {
                sql1 = "SELECT * FROM `returns` , stock WHERE stock.branch = '" + branchId + "' AND  returns.name = stock.ID AND  returns.date = '" + LocalDate.now() + "' AND stock.ID !=0;";
                sql2 = "SELECT * FROM `sales` , stock WHERE stock.branch = '" + branchId + "' AND  sales.stock_id = stock.ID AND  sales.date = '" + LocalDate.now() + "' AND stock.ID !=0;";
            }
            try {
                db.rs = db.st.executeQuery(sql1);
                while (db.rs.next()) {
                    temp1 += db.rs.getFloat("total");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
            try {
                db.rs = db.st.executeQuery(sql2);
                while (db.rs.next()) {
                    temp += db.rs.getFloat("total");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
        } catch (Exception e) {
        }
        inLabel.setText(String.valueOf(temp - temp1));
        if (Float.parseFloat(inLabel.getText()) < Float.parseFloat(outLabel.getText())) {
            decideImgLbl.setVisible(true);
            decideImgLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thumb_down.png")));
        } else {
            decideImgLbl.setVisible(true);
            decideImgLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thumb_up.png")));
        }
    }//GEN-LAST:event_dayLbl1MousePressed

    private void monthLbl1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_monthLbl1MousePressed
        setLabelIcon(dayLbl1, "/images/day_white.png");
        setLabelIcon(monthLbl1, "/images/month_dark.png");
        setLabelIcon(yearLbl1, "/images/year_white.png");

        // profits in month 
        DBcon d = new DBcon();
        DBcon db = new DBcon();
        String branch = String.valueOf(profitsComboBox1.getSelectedItem());
        float temp = 0;
        float temp1 = 0;
        int stock_id = 0;
        String sql = null;
        String sql1;
        String sql2;

        try {
            int branchId = 0;
            d.rs = d.st.executeQuery("select * from branch WHERE name = '" + branch + "';");
            while ((d.rs).next()) {
                branchId = d.rs.getInt("id");
            }
            if (branch.equals("all")) {
                sql1 = "SELECT * FROM `returns` WHERE date BETWEEN '" + LocalDate.now().minusMonths(1) + "' AND '" + LocalDate.now() + "' AND stock.ID !=0;";
                sql2 = "SELECT * FROM `sales` WHERE  date BETWEEN '" + LocalDate.now().minusMonths(1) + "' AND '" + LocalDate.now() + "' AND stock.ID !=0; ";
            } else {
                sql1 = "SELECT * FROM `returns` , stock WHERE stock.branch = '" + branchId + "' AND  returns.name = stock.ID AND  returns.date BETWEEN '" + LocalDate.now().minusMonths(1) + "' AND '" + LocalDate.now() + "' AND stock.ID !=0;";
                sql2 = "SELECT * FROM `sales` , stock WHERE stock.branch = '" + branchId + "' AND  sales.stock_id = stock.ID AND  sales.date BETWEEN '" + LocalDate.now().minusMonths(1) + "' AND '" + LocalDate.now() + "' AND stock.ID !=0;";
            }
            try {

                db.rs = db.st.executeQuery(sql1);
                while (db.rs.next()) {
                    temp1 += db.rs.getFloat("total");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
            try {
                db.rs = db.st.executeQuery(sql2);
                while (db.rs.next()) {
                    temp += db.rs.getFloat("total");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
        } catch (Exception e) {
        }
        inLabel.setText(String.valueOf(temp - temp1));

        if (Float.parseFloat(inLabel.getText()) < Float.parseFloat(outLabel.getText())) {

            decideImgLbl.setVisible(true);
            decideImgLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thumb_down.png")));
        } else {
            decideImgLbl.setVisible(true);
            decideImgLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thumb_up.png")));
        }
    }//GEN-LAST:event_monthLbl1MousePressed

    private void yearLbl1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_yearLbl1MousePressed
        setLabelIcon(dayLbl1, "/images/day_white.png");
        setLabelIcon(monthLbl1, "/images/month_white.png");
        setLabelIcon(yearLbl1, "/images/year_dark.png");

        // profits in year 
        DBcon d = new DBcon();
        DBcon db = new DBcon();
        String branch = String.valueOf(profitsComboBox1.getSelectedItem());
        float temp = 0;
        float temp1 = 0;
        int stock_id = 0;
        String sql = null;
        String sql1;
        String sql2;
        int branchId = 0;
        try {
            d.rs = d.st.executeQuery("select * from branch WHERE name = '" + branch + "';");
            while ((d.rs).next()) {
                branchId = d.rs.getInt("id");
            }
            if (branch.equals("all")) {
                sql1 = "SELECT * FROM `returns` WHERE date BETWEEN '" + LocalDate.now().minusYears(1) + "' AND '" + LocalDate.now() + "' AND stock.ID !=0;";
                sql2 = "SELECT * FROM `sales` WHERE  date BETWEEN '" + LocalDate.now().minusYears(1) + "' AND '" + LocalDate.now() + "' AND stock.ID !=0;";
            } else {
                sql1 = "SELECT * FROM `returns` , stock WHERE stock.branch = '" + branchId + "' AND  returns.name = stock.ID AND  returns.date BETWEEN '" + LocalDate.now().minusYears(1) + "' AND '" + LocalDate.now() + "' AND stock.ID !=0;";
                sql2 = "SELECT * FROM `sales` , stock WHERE stock.branch = '" + branchId + "' AND  sales.stock_id = stock.ID AND  sales.date BETWEEN '" + LocalDate.now().minusYears(1) + "' AND '" + LocalDate.now() + "' AND stock.ID !=0;";
            }

            try {

                db.rs = db.st.executeQuery(sql1);
                while (db.rs.next()) {
                    temp1 += db.rs.getFloat("total");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }

            try {
                db.rs = db.st.executeQuery(sql2);
                while (db.rs.next()) {
                    temp += db.rs.getFloat("total");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }

        } catch (Exception e) {
        }
        inLabel.setText(String.valueOf(temp - temp1));

        if (Float.parseFloat(inLabel.getText()) < Float.parseFloat(outLabel.getText())) {

            decideImgLbl.setVisible(true);
            decideImgLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thumb_down.png")));
        } else {
            decideImgLbl.setVisible(true);
            decideImgLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thumb_up.png")));
        }

    }//GEN-LAST:event_yearLbl1MousePressed

    private void dayLbl2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dayLbl2MousePressed
        setLabelIcon(dayLbl2, "/images/day_dark.png");
        setLabelIcon(monthLbl2, "/images/month_blue.png");
        setLabelIcon(yearLbl2, "/images/year_blue.png");
        // profits out day 
        DBcon db = new DBcon();
        DBcon d = new DBcon();
        DBcon dd = new DBcon();
        int quant = 0;
        float temp = 0, paidr = 0, price = 0, rest = 0;
        String branch = String.valueOf(profitsComboBox1.getSelectedItem());
        String sql = null;
        String sql2 = null;
        float temp1 = 0, temp2 = 0;
        int checkId = 0;
        long stockId = 0;
        String stockName = null;

        try {
            int branchId = 0;
            db.rs = db.st.executeQuery("select * from branch WHERE name = '" + branch + "';");
            while ((db.rs).next()) {
                branchId = db.rs.getInt("id");
            }
            if (branch.equals("all")) {
                sql = "SELECT * FROM `returns` , stock WHERE returns.name = stock.ID AND returns.date = '" + LocalDate.now() + "' AND stock.ID !=0;";
                sql2 = "SELECT * FROM `sales` ,stock  WHERE sales.stock_id = stock.id AND sales.date = '" + LocalDate.now() + "';";
            } else {
                sql = "SELECT * FROM `returns` , stock WHERE returns.name = stock.ID AND returns.date = '" + LocalDate.now() + "' AND stock.branch = '" + branchId + "' AND stock.ID !=0;";
                sql2 = "SELECT * FROM `sales` , stock  WHERE sales.stock_id = stock.id AND  sales.date = '" + LocalDate.now() + "' AND stock.branch = '" + branchId + "' AND stock.ID !=0;";
            }
            try {
                db.rs = db.st.executeQuery(sql);
                while (db.rs.next()) {
                    temp1 += db.rs.getFloat("stock.price") * db.rs.getInt("returns.quantity");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
            try {
                db.rs = db.st.executeQuery(sql2);
                while (db.rs.next()) {
                    quant = db.rs.getInt("sales.quantity");
                    price = db.rs.getFloat("stock.Price");
                    checkId = db.rs.getInt("sales.check_id");
                    stockId = db.rs.getLong("stock.ID");
                    if (stockId == -1) {
                        d.rs = d.st.executeQuery("select * from multisales WHERE ID = '" + checkId + "';");
                        while (d.rs.next()) {
                            stockName = d.rs.getString("Item");
                            dd.rs = dd.st.executeQuery("select * from stock WHERE Name = '" + stockName + "';");
                            dd.rs.last();
                            price = dd.rs.getFloat("stock.Price");
                            quant = d.rs.getInt("multisales.Quant");
                            temp += (price * (float) quant);
                        }
                    } else {
                        temp += (price * (float) quant);
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري" + ex);
            }
            try {
                db.rs = db.st.executeQuery("select * from outgoings where inProfits = 1 AND outgoings.date = '" + LocalDate.now() + "'");
                while (db.rs.next()) {
                    temp2 += db.rs.getFloat("outgoings.total");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        outLabel.setText(String.valueOf((temp + temp2) - temp1));
        if (Float.parseFloat(inLabel.getText()) < Float.parseFloat(outLabel.getText())) {

            decideImgLbl.setVisible(true);
            decideImgLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thumb_down.png")));
        } else {
            decideImgLbl.setVisible(true);
            decideImgLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thumb_up.png")));
        }
    }//GEN-LAST:event_dayLbl2MousePressed

    private void monthLbl2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_monthLbl2MousePressed
        setLabelIcon(dayLbl2, "/images/day_blue.png");
        setLabelIcon(monthLbl2, "/images/month_dark.png");
        setLabelIcon(yearLbl2, "/images/year_blue.png");

        // profits out month 
        DBcon db = new DBcon();
        DBcon d = new DBcon();
        DBcon dd = new DBcon();
        int quant = 0;
        float temp = 0, paidr = 0, price = 0, rest = 0;
        String branch = String.valueOf(profitsComboBox1.getSelectedItem());
        String sql = null;
        String sql2 = null;
        float temp1 = 0, temp2 = 0;
        int checkId = 0;
        long stockId = 0;
        String stockName = null;
        try {
            int branchId = 0;
            db.rs = db.st.executeQuery("select * from branch WHERE name = '" + branch + "';");
            while ((db.rs).next()) {
                branchId = db.rs.getInt("id");
            }
            if (branch.equals("all")) {
                sql = "SELECT * FROM `returns` , stock WHERE returns.name = stock.ID AND returns.date BETWEEN '" + LocalDate.now().minusMonths(1) + "' AND '" + LocalDate.now() + "' AND stock.ID !=0;";
                sql2 = "SELECT * FROM `sales` ,stock  WHERE sales.stock_id = stock.id AND sales.date BETWEEN '" + LocalDate.now().minusMonths(1) + "' AND '" + LocalDate.now() + "' AND stock.ID !=0;";
            } else {
                sql = "SELECT * FROM `returns` , stock WHERE returns.name = stock.ID AND returns.date BETWEEN '" + LocalDate.now().minusMonths(1) + "' AND '" + LocalDate.now() + "' AND stock.branch = '" + branchId + "' AND stock.ID !=0;";
                sql2 = "SELECT * FROM `sales` , stock  WHERE sales.stock_id = stock.id AND  sales.date BETWEEN '" + LocalDate.now().minusMonths(1) + "' AND '" + LocalDate.now() + "' AND stock.branch = '" + branchId + "' AND stock.ID !=0;";
            }
            try {
                db.rs = db.st.executeQuery(sql);
                while (db.rs.next()) {
                    temp1 += db.rs.getFloat("stock.price") * db.rs.getInt("returns.quantity");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
            try {
                db.rs = db.st.executeQuery(sql2);
                while (db.rs.next()) {
                    quant = db.rs.getInt("sales.quantity");
                    price = db.rs.getFloat("stock.Price");
                    checkId = db.rs.getInt("sales.check_id");
                    stockId = db.rs.getLong("stock.ID");
                    if (stockId == -1) {
                        d.rs = d.st.executeQuery("select * from multisales WHERE ID = '" + checkId + "';");
                        while (d.rs.next()) {
                            stockName = d.rs.getString("Item");
                            dd.rs = dd.st.executeQuery("select * from stock WHERE Name = '" + stockName + "';");
                            dd.rs.last();
                            price = dd.rs.getFloat("stock.Price");
                            quant = d.rs.getInt("multisales.Quant");
                            temp += (price * (float) quant);
                        }
                    } else {
                        temp += (price * (float) quant);
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
            try {
                db.rs = db.st.executeQuery("select * from outgoings where inProfits = 1 AND outgoings.date BETWEEN '" + LocalDate.now().minusMonths(1) + "' AND '" + LocalDate.now() + "'");
                while (db.rs.next()) {
                    temp2 += db.rs.getFloat("outgoings.total");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        outLabel.setText(String.valueOf((temp + temp2) - temp1));
        if (Float.parseFloat(inLabel.getText()) < Float.parseFloat(outLabel.getText())) {

            decideImgLbl.setVisible(true);
            decideImgLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thumb_down.png")));
        } else {
            decideImgLbl.setVisible(true);
            decideImgLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thumb_up.png")));
        }
    }//GEN-LAST:event_monthLbl2MousePressed

    private void yearLbl2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_yearLbl2MousePressed
        setLabelIcon(dayLbl2, "/images/day_blue.png");
        setLabelIcon(monthLbl2, "/images/month_blue.png");
        setLabelIcon(yearLbl2, "/images/year_dark.png");

        // profits in year 
        DBcon db = new DBcon();
        DBcon d = new DBcon();
        DBcon dd = new DBcon();
        int quant = 0;
        float temp = 0, paidr = 0, price = 0, rest = 0;
        String branch = String.valueOf(profitsComboBox1.getSelectedItem());
        String sql = null;
        String sql2 = null;
        float temp1 = 0, temp2 = 0;
        int checkId = 0;
        long stockId = 0;
        String stockName = null;
        try {
            int branchId = 0;
            db.rs = db.st.executeQuery("select * from branch WHERE name = '" + branch + "';");
            while ((db.rs).next()) {
                branchId = db.rs.getInt("id");
            }
            if (branch.equals("all")) {
                sql = "SELECT * FROM `returns` , stock WHERE returns.name = stock.ID AND returns.date BETWEEN '" + LocalDate.now().minusYears(1) + "' AND '" + LocalDate.now() + "' AND stock.ID !=0;";
                sql2 = "SELECT * FROM `sales` ,stock  WHERE sales.stock_id = stock.id AND sales.date BETWEEN '" + LocalDate.now().minusYears(1) + "' AND '" + LocalDate.now() + "' AND stock.ID !=0;";
            } else {
                sql = "SELECT * FROM `returns` , stock WHERE returns.name = stock.ID AND returns.date BETWEEN '" + LocalDate.now().minusYears(1) + "' AND '" + LocalDate.now() + "' AND stock.branch = '" + branchId + "' AND stock.ID !=0;";
                sql2 = "SELECT * FROM `sales` , stock  WHERE sales.stock_id = stock.id AND  sales.date BETWEEN '" + LocalDate.now().minusYears(1) + "' AND '" + LocalDate.now() + "' AND stock.branch = '" + branchId + "' AND stock.ID !=0;";
            }
            try {
                db.rs = db.st.executeQuery(sql);
                while (db.rs.next()) {
                    temp1 += db.rs.getFloat("stock.price") * db.rs.getInt("returns.quantity");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
            try {
                db.rs = db.st.executeQuery(sql2);
                while (db.rs.next()) {
                    db.rs = db.st.executeQuery(sql2);
                    while (db.rs.next()) {
                        quant = db.rs.getInt("sales.quantity");
                        price = db.rs.getFloat("stock.Price");
                        checkId = db.rs.getInt("sales.check_id");
                        stockId = db.rs.getLong("stock.ID");
                        if (stockId == -1) {
                            d.rs = d.st.executeQuery("select * from multisales WHERE ID = '" + checkId + "';");
                            while (d.rs.next()) {
                                stockName = d.rs.getString("Item");
                                dd.rs = dd.st.executeQuery("select * from stock WHERE Name = '" + stockName + "';");
                                dd.rs.last();
                                price = dd.rs.getFloat("stock.Price");
                                quant = d.rs.getInt("multisales.Quant");
                                temp += (price * (float) quant);
                            }
                        } else {
                            temp += (price * (float) quant);
                        }
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
            try {
                db.rs = db.st.executeQuery("select * from outgoings where inProfits = 1 AND outgoings.date BETWEEN '" + LocalDate.now().minusYears(1) + "' AND '" + LocalDate.now() + "'");
                while (db.rs.next()) {
                    temp2 += db.rs.getFloat("outgoings.total");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        outLabel.setText(String.valueOf((temp + temp2) - temp1));
        if (Float.parseFloat(inLabel.getText()) < Float.parseFloat(outLabel.getText())) {

            decideImgLbl.setVisible(true);
            decideImgLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thumb_down.png")));
        } else {
            decideImgLbl.setVisible(true);
            decideImgLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thumb_up.png")));
        }
    }//GEN-LAST:event_yearLbl2MousePressed

    private void dayLbl3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dayLbl3MousePressed
        setLabelIcon(dayLbl3, "/images/day_dark.png");
        setLabelIcon(monthLbl3, "/images/month_white.png");
        setLabelIcon(yearLbl3, "/images/year_white.png");

        // inventory day
        whatTime = 1;
        //  LocalDate.
        if (inventoryTable1.getRowCount() != 0) {
            for (int i = inventoryTable1.getRowCount() - 1; i >= 0; i--) {
                inventoryTable1.removeRow(i);
            }
        }
        if (inventoryTable2.getRowCount() != 0) {
            for (int i = inventoryTable2.getRowCount() - 1; i >= 0; i--) {
                inventoryTable2.removeRow(i);
            }
        }
        DBcon db = new DBcon();
        String branch = null;
        String sql = null, sql3 = null, sql2 = null;
        branch = String.valueOf(inventoryComboBox.getSelectedItem());
        int branchId = 0;
        try {
            db.rs = db.st.executeQuery("select * from branch WHERE name = '" + branch + "';");
            while ((db.rs).next()) {
                branchId = db.rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        if (branch.equals("all")) {
            sql = "SELECT * FROM `returns`,stock WHERE returns.name = stock.ID AND returns.date = '" + LocalDate.now() + "';";
            sql3 = "SELECT * FROM `sales`,stock,clients WHERE sales.stock_id = stock.ID AND sales.client_id = clients.ID AND sales.date = '" + LocalDate.now() + "' order by date DESC;";
            sql2 = "SELECT * FROM `purchases`,stock ,clients WHERE purchases.stock_id = stock.ID AND purchases.client_id = clients.ID AND purchases.date = '" + LocalDate.now() + "' order by date DESC;";
        } else {
            sql = "SELECT * FROM `returns`,stock WHERE returns.name = stock.ID AND stock.branch='" + branchId + "' AND returns.date = '" + LocalDate.now() + "';";
            sql3 = "SELECT * FROM `sales`,stock ,clients WHERE sales.stock_id = stock.ID AND sales.client_id = clients.ID AND stock.branch='" + branchId + "' AND sales.date = '" + LocalDate.now() + "' order by sales.date DESC;";
            sql2 = "SELECT * FROM `purchases`,stock ,clients WHERE purchases.stock_id = stock.ID AND purchases.client_id = clients.ID AND stock.branch='" + branchId + "' AND purchases.date = '" + LocalDate.now() + "' order by purchases.date DESC;";
        }
        String date = null;
        String rest, total;
        String quant1, name1 = "unknown", Item = null, price = null;
        float temp1 = 0;
        try {
            db.rs = db.st.executeQuery(sql);
            while (db.rs.next()) {
                temp1 += db.rs.getFloat("total");
            }
            return_inventory.setText(String.valueOf(temp1));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
        try {
            db.rs = db.st.executeQuery(sql3);
            float in = 0;
            while (db.rs.next()) {
                quant1 = String.valueOf(db.rs.getInt("sales.quantity"));
                name1 = db.rs.getString("clients.Name");
                Item = db.rs.getString("stock.Name");
                // rest = String.valueOf(db.rs.getFloat("sales.paid"));
                total = String.valueOf(db.rs.getFloat("sales.total"));
                date = db.rs.getString("sales.date");

                // price = String.valueOf(db.rs.getFloat("sales.total") / db.rs.getInt("sales.quantity"));
                String[] rowData = {Item, name1, quant1, total, date};
                inventoryTable1.addRow(rowData);
                in += db.rs.getFloat("total");
            }
            inLabel2.setText(String.valueOf(in));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
        try {
            float in = 0;
            db.rs = db.st.executeQuery(sql2);
            while (db.rs.next()) {
                quant1 = String.valueOf(db.rs.getInt("purchases.quantity"));
                name1 = db.rs.getString("clients.Name");
                Item = db.rs.getString("stock.Name");
                date = db.rs.getString("purchases.date");
                //rest = String.valueOf(db.rs.getFloat("purchases.paid"));
                total = String.valueOf(db.rs.getFloat("purchases.total"));
                // price = String.valueOf(db.rs.getFloat("purchases.total") / db.rs.getInt("purchases.quantity"));
                String[] rowData = {Item, name1, quant1, total, date};
                inventoryTable2.addRow(rowData);
                in += db.rs.getFloat("purchases.total");
            }
            db.rs = db.st.executeQuery("SELECT * FROM `outgoings`,clients WHERE  outgoings.client_id = clients.ID  AND outgoings.inProfits = '" + 1 + "' AND outgoings.date = '" + LocalDate.now() + "' order by outgoings.date DESC;");
            while (db.rs.next()) {
                name1 = db.rs.getString("clients.Name");
                Item = db.rs.getString("outgoings.stock_name");
                date = db.rs.getString("outgoings.date");
                total = String.valueOf(db.rs.getFloat("outgoings.total"));
                String[] rowData = {Item, name1, "0", total, date};
                inventoryTable2.addRow(rowData);
                in += db.rs.getFloat("outgoings.total");
            }

            inLabel1.setText(String.valueOf(in));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
        salesT.setAutoCreateRowSorter(true);
        inventoryTable2.fireTableStructureChanged();
    }//GEN-LAST:event_dayLbl3MousePressed

    private void monthLbl3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_monthLbl3MousePressed
        setLabelIcon(dayLbl3, "/images/day_white.png");
        setLabelIcon(monthLbl3, "/images/month_dark.png");
        setLabelIcon(yearLbl3, "/images/year_white.png");

        // inventory month
        whatTime = 2;
        if (inventoryTable1.getRowCount() != 0) {
            for (int i = inventoryTable1.getRowCount() - 1; i >= 0; i--) {
                inventoryTable1.removeRow(i);
            }
        }
        if (inventoryTable2.getRowCount() != 0) {
            for (int i = inventoryTable2.getRowCount() - 1; i >= 0; i--) {
                inventoryTable2.removeRow(i);
            }
        }
        DBcon db = new DBcon();
        String branch = null;
        String sql = null, sql3 = null, sql2 = null;
        branch = String.valueOf(inventoryComboBox.getSelectedItem());
        int branchId = 0;
        try {
            db.rs = db.st.executeQuery("select * from branch WHERE name = '" + branch + "';");
            while ((db.rs).next()) {
                branchId = db.rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        if (branch.equals("all")) {
            sql = "SELECT * FROM `returns`,stock WHERE returns.name = stock.ID AND returns.date BETWEEN '" + LocalDate.now().minusMonths(1) + "' AND '" + LocalDate.now() + "';";
            sql3 = "SELECT * FROM `sales`,stock,clients WHERE sales.stock_id = stock.ID AND sales.client_id = clients.ID AND sales.date BETWEEN '" + LocalDate.now().minusMonths(1) + "' AND '" + LocalDate.now() + "' order by sales.date DESC;";
            sql2 = "SELECT * FROM `purchases`,stock ,clients WHERE purchases.stock_id = stock.ID AND purchases.client_id = clients.ID AND purchases.date BETWEEN '" + LocalDate.now().minusMonths(1) + "' AND '" + LocalDate.now() + "' order by purchases.date DESC;";
        } else {
            sql = "SELECT * FROM `returns`,stock WHERE returns.name = stock.ID AND stock.branch='" + branchId + "' AND returns.date BETWEEN '" + LocalDate.now().minusMonths(1) + "' AND '" + LocalDate.now() + "';";
            sql3 = "SELECT * FROM `sales`,stock ,clients WHERE sales.stock_id = stock.ID AND sales.client_id = clients.ID AND stock.branch='" + branchId + "' AND sales.date BETWEEN '" + LocalDate.now().minusMonths(1) + "' AND '" + LocalDate.now() + "' order by sales.date DESC;";
            sql2 = "SELECT * FROM `purchases`,stock ,clients WHERE purchases.stock_id = stock.ID AND purchases.client_id = clients.ID AND stock.branch='" + branchId + "' AND purchases.date BETWEEN '" + LocalDate.now().minusMonths(1) + "' AND '" + LocalDate.now() + "' order by purchases.date DESC;";
        }

        String rest, total, date;
        String quant1, name1 = "unknown", Item = null, price = null;
        float temp1 = 0;
        try {
            db.rs = db.st.executeQuery(sql);
            while (db.rs.next()) {
                temp1 += db.rs.getFloat("total");
            }
            return_inventory.setText(String.valueOf(temp1));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
        try {
            float in = 0;
            db.rs = db.st.executeQuery(sql3);

            while (db.rs.next()) {
                quant1 = String.valueOf(db.rs.getInt("sales.quantity"));
                name1 = db.rs.getString("clients.Name");
                Item = db.rs.getString("stock.Name");
                // rest = String.valueOf(db.rs.getFloat("sales.paid"));
                total = String.valueOf(db.rs.getFloat("sales.total"));
                date = db.rs.getString("sales.date");
                // price = String.valueOf(db.rs.getFloat("sales.total") / db.rs.getInt("sales.quantity"));
                String[] rowData = {Item, name1, quant1, total, date};
                inventoryTable1.addRow(rowData);
                in += db.rs.getFloat("total");
            }
            inLabel2.setText(String.valueOf(in));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
        try {
            db.rs = db.st.executeQuery(sql2);
            float in = 0;
            while (db.rs.next()) {
                quant1 = String.valueOf(db.rs.getInt("purchases.quantity"));
                name1 = db.rs.getString("clients.Name");
                Item = db.rs.getString("stock.Name");
                // rest = String.valueOf(db.rs.getFloat("purchases.paid"));
                total = String.valueOf(db.rs.getFloat("purchases.total"));
                date = db.rs.getString("purchases.date");
                // price = String.valueOf(db.rs.getFloat("purchases.total") / db.rs.getInt("purchases.quantity"));
                String[] rowData = {Item, name1, quant1, total, date};
                inventoryTable2.addRow(rowData);
                in += db.rs.getFloat("purchases.total");
            }
            db.rs = db.st.executeQuery("SELECT * FROM `outgoings`,clients WHERE  outgoings.client_id = clients.ID  AND outgoings.inProfits = '" + 1 + "' AND outgoings.date BETWEEN '" + LocalDate.now().minusMonths(1) + "' AND '" + LocalDate.now() + "' order by outgoings.date DESC;");
            while (db.rs.next()) {
                name1 = db.rs.getString("clients.Name");
                Item = db.rs.getString("outgoings.stock_name");
                date = db.rs.getString("outgoings.date");
                total = String.valueOf(db.rs.getFloat("outgoings.total"));
                String[] rowData = {Item, name1, "0", total, date};
                inventoryTable2.addRow(rowData);
                in += db.rs.getFloat("outgoings.total");
            }

            inLabel1.setText(String.valueOf(in));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }

    }//GEN-LAST:event_monthLbl3MousePressed

    private void yearLbl3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_yearLbl3MousePressed
        setLabelIcon(dayLbl3, "/images/day_white.png");
        setLabelIcon(monthLbl3, "/images/month_white.png");
        setLabelIcon(yearLbl3, "/images/year_dark.png");

        // invntory year
        whatTime = 3;
        if (inventoryTable1.getRowCount() != 0) {
            for (int i = inventoryTable1.getRowCount() - 1; i >= 0; i--) {
                inventoryTable1.removeRow(i);
            }
        }
        if (inventoryTable2.getRowCount() != 0) {
            for (int i = inventoryTable2.getRowCount() - 1; i >= 0; i--) {
                inventoryTable2.removeRow(i);
            }
        }
        DBcon db = new DBcon();
        String branch = null;
        String sql = null, sql3 = null, sql2 = null;
        branch = String.valueOf(inventoryComboBox.getSelectedItem());
        int branchId = 0;
        try {
            db.rs = db.st.executeQuery("select * from branch WHERE name = '" + branch + "';");
            while ((db.rs).next()) {
                branchId = db.rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        if (branch.equals("all")) {
            sql = "SELECT * FROM `returns`,stock WHERE returns.name = stock.ID AND returns.date BETWEEN '" + LocalDate.now().minusYears(1) + "' AND '" + LocalDate.now() + "';";
            sql3 = "SELECT * FROM `sales`,stock,clients WHERE sales.stock_id = stock.ID AND sales.client_id = clients.ID AND sales.date BETWEEN '" + LocalDate.now().minusYears(1) + "' AND '" + LocalDate.now() + "' order by sales.date DESC;";
            sql2 = "SELECT * FROM `purchases`,stock ,clients WHERE purchases.stock_id = stock.ID AND purchases.client_id = clients.ID AND purchases.date BETWEEN '" + LocalDate.now().minusYears(1) + "' AND '" + LocalDate.now() + "' order by purchases.date DESC;";
        } else {
            sql = "SELECT * FROM `returns`,stock WHERE returns.name = stock.ID AND stock.branch='" + branchId + "' AND returns.date BETWEEN '" + LocalDate.now().minusYears(1) + "' AND '" + LocalDate.now() + "';";
            sql3 = "SELECT * FROM `sales`,stock ,clients WHERE sales.stock_id = stock.ID AND sales.client_id = clients.ID AND stock.branch='" + branchId + "' AND sales.date BETWEEN '" + LocalDate.now().minusYears(1) + "' AND '" + LocalDate.now() + "' order by sales.date DESC;";
            sql2 = "SELECT * FROM `purchases`,stock ,clients WHERE purchases.stock_id = stock.ID AND purchases.client_id = clients.ID AND stock.branch='" + branchId + "' AND purchases.date BETWEEN '" + LocalDate.now().minusYears(1) + "' AND '" + LocalDate.now() + "' order by purchases.date DESC;";
        }
        String rest, total, date;
        String quant1, name1 = "unknown", Item = null, price = null;
        float temp1 = 0;
        try {
            db.rs = db.st.executeQuery(sql);
            while (db.rs.next()) {
                temp1 += db.rs.getFloat("total");
            }
            return_inventory.setText(String.valueOf(temp1));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
        try {
            db.rs = db.st.executeQuery(sql3);
            float in = 0;
            while (db.rs.next()) {
                quant1 = String.valueOf(db.rs.getInt("sales.quantity"));
                name1 = db.rs.getString("clients.Name");
                Item = db.rs.getString("stock.Name");
                //rest = String.valueOf(db.rs.getFloat("sales.paid"));
                total = String.valueOf(db.rs.getFloat("sales.total"));
                date = db.rs.getString("sales.date");

                //price = String.valueOf(db.rs.getFloat("sales.total") / db.rs.getInt("sales.quantity"));
                String[] rowData = {Item, name1, quant1, total, date};
                inventoryTable1.addRow(rowData);
                in += db.rs.getFloat("total");
            }
            inLabel2.setText(String.valueOf(in));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
        try {
            db.rs = db.st.executeQuery(sql2);
            float in = 0;
            while (db.rs.next()) {
                quant1 = String.valueOf(db.rs.getInt("purchases.quantity"));
                name1 = db.rs.getString("clients.Name");
                Item = db.rs.getString("stock.Name");
                date = db.rs.getString("purchases.date");
                total = String.valueOf(db.rs.getFloat("purchases.total"));
                String[] rowData = {Item, name1, quant1, total, date};
                inventoryTable2.addRow(rowData);
                in += db.rs.getFloat("purchases.total");
            }
            db.rs = db.st.executeQuery("SELECT * FROM `outgoings`,clients WHERE  outgoings.client_id = clients.ID  AND outgoings.inProfits = '" + 1 + "' AND outgoings.date BETWEEN '" + LocalDate.now().minusMonths(1) + "' AND '" + LocalDate.now() + "' order by outgoings.date DESC;");
            while (db.rs.next()) {
                name1 = db.rs.getString("clients.Name");
                Item = db.rs.getString("outgoings.stock_name");
                date = db.rs.getString("outgoings.date");
                total = String.valueOf(db.rs.getFloat("outgoings.total"));
                String[] rowData = {Item, name1, "0", total, date};
                inventoryTable2.addRow(rowData);
                in += db.rs.getFloat("outgoings.total");
            }

            inLabel1.setText(String.valueOf(in));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }

    }//GEN-LAST:event_yearLbl3MousePressed

    private void TotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalActionPerformed

    private void myDeptEMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_myDeptEMouseExited
        myDeptE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye2.png")));
    }//GEN-LAST:event_myDeptEMouseExited

    private void myDeptEMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_myDeptEMouseEntered
        myDeptE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye1.png")));
    }//GEN-LAST:event_myDeptEMouseEntered

    private void deptMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deptMouseExited
        dept.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye2.png")));
    }//GEN-LAST:event_deptMouseExited

    private void deptMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deptMouseEntered
        dept.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye1.png")));
    }//GEN-LAST:event_deptMouseEntered

    private void lang_lblMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lang_lblMousePressed
        if (lang_lbl.getText().equals("عربي")) {
            lang_lbl.setText("English");
            convertLang(new JLabel[]{type_lbl1, label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14,
                stock_lbl1, orders_lbl1, sales_lbl1, purchase_lbl1, returns_lbl1, cons_lbl1,
                add_lbl2, add_lbl3, add_lbl4, add_lbl7, add_lbl8, add_lbl11, add_hours,
                cName_lbl3, cName_lbl4, cName_lbl5, cName_lbl7, cName_lbl8, cName_lbl9, cName_lbl10,
                ch_lbl3, ch_lbl4, ch_lbl5, del_lbl2, del_lbl3, del_lbl4, del_lbl8, del_lbl11, del_lbl12,
                edit_lbl2, edit_lbl3, edit_lbl4, edit_lbl8, edit_lbl11, edit_lbl12,
                id_lbl2, id_lbl6, id_lbl7, id_lbl8, id_lbl11, mob_lbl3, mob_lbl4, mob_lbl11,
                name_lbl2, name_lbl3, name_lbl4, name_lbl5, name_lbl6, name_lbl7, name_lbl8, name_lbl11,
                paid_lbl3, paid_lbl4, paid_lbl7, paid_lbl8,
                price_lbl2, branch_lbl2, price_lbl6, price_lbl3, price_lbl7, price_lbl8,
                quan_lbl2, quan_lbl3, quan_lbl6, quan_lbl7, quan_lbl8,
                rest_lbl3, rest_lbl4, rest_lbl7, rest_lbl8, rest_lbl5, date_lbl7, rest_lbl10, date_lbl4,
                total_lbl3, notes_lbl, total_lbl5, total_lbl7, total_lbl8, user_lbl12, total_lbl6,
                view_lbl2, view_lbl3, view_lbl4, view_lbl6, view_lbl7, view_lbl8, view_lbl11, view_lbl12,
                search_lbl2, search_lbl3, search_lbl4, search_lbl6, search_lbl7, search_lbl8, search_lbl11, search_lbl12,
                dept_lbl11, pass_lbl12, mob_lbl6, deptL2, deptL1, inLabel4, inLabel5, inLabel6, inLabel7, type_lbl12, mob_lbl6, mob_lbl5,
                cName_lbl9, cName_lbl10,
                check_lbl1, check_lbl2, check_lbl3, check_lbl4, check_lbl5, check_lbl6, check_lbl7, check_lbl8, check_lbl9, check_lbl10, return_lbl, from_lbl1, to_lbl1, from_lbl2, to_lbl2, hours_lbl,
                branch_lbl2, branch_lbl3, branch_lbl6, branch_lbl7, branch_lbl9, branch_lbl10, branch_lbl12,
                alarm_lbl2, ok_lbl1, ok_lbl2, item_lbl3, payment_lbl, notes_lbl, total_lbl9, id2_lbl9, dept_lbl00, dept_lbl01, order_dept_lbl, return_dept_lbl,
                outLabel9, inLabel9
            }, 1);
            /*
            changeFontToArabic(new JLabel[]{type_lbl1, label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14,
                 stock_lbl1, orders_lbl1, sales_lbl1, purchase_lbl1, returns_lbl1, cons_lbl1,
                 add_lbl2, add_lbl3, add_lbl4, add_lbl7, add_lbl8, add_lbl11, add_lbl12,
                 cName_lbl3, cName_lbl4, cName_lbl5, cName_lbl7, cName_lbl8,
                 ch_lbl3, ch_lbl4, ch_lbl5, del_lbl2, del_lbl3, del_lbl4, del_lbl8, del_lbl11, del_lbl12,
                 edit_lbl2, edit_lbl3, edit_lbl4, edit_lbl7, edit_lbl8, edit_lbl11, edit_lbl12,
                 id_lbl2, id_lbl6, id_lbl7, id_lbl8, id_lbl11, mob_lbl3, mob_lbl4, mob_lbl11,
                 name_lbl2, name_lbl3, name_lbl4, name_lbl5, name_lbl6, name_lbl7, name_lbl8, name_lbl11, name_lbl12,
                 paid_lbl3, paid_lbl4, paid_lbl7, paid_lbl8,
                 price_lbl2, price_lbl3, price_lbl4, price_lbl7, price_lbl8,
                 quan_lbl2, quan_lbl3, quan_lbl4, quan_lbl6, quan_lbl7, quan_lbl8,
                 rest_lbl3, rest_lbl4, rest_lbl7, rest_lbl8,
                 total_lbl3, total_lbl4, total_lbl5, total_lbl7, total_lbl8, total_lbl12, user_lbl12,
                 view_lbl2, view_lbl3, view_lbl4, view_lbl6, view_lbl7, view_lbl8, view_lbl11, view_lbl12,
                 search_lbl2, search_lbl3, search_lbl4, search_lbl6, search_lbl7, search_lbl8, search_lbl11, search_lbl12,
                 dept_lbl11, mail_lbl11, pass_lbl12
            }, "Segoe UI Semibold");
             */
        } else {
            lang_lbl.setText("عربي");
            convertLang(new JLabel[]{type_lbl1, label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14,
                stock_lbl1, orders_lbl1, sales_lbl1, purchase_lbl1, returns_lbl1, cons_lbl1,
                add_lbl2, add_lbl3, add_lbl4, add_lbl7, add_lbl8, add_lbl11, add_hours,
                cName_lbl3, cName_lbl4, cName_lbl5, cName_lbl7, cName_lbl8, cName_lbl9, cName_lbl10,
                ch_lbl3, ch_lbl4, ch_lbl5, del_lbl2, del_lbl3, del_lbl4, del_lbl8, del_lbl11, del_lbl12,
                edit_lbl2, edit_lbl3, edit_lbl4, edit_lbl8, edit_lbl11, edit_lbl12,
                id_lbl2, id_lbl6, id_lbl7, id_lbl8, id_lbl11, mob_lbl3, mob_lbl4, mob_lbl11,
                name_lbl2, name_lbl3, name_lbl4, name_lbl5, name_lbl6, name_lbl7, name_lbl8, name_lbl11,
                paid_lbl3, paid_lbl4, paid_lbl7, paid_lbl8,
                price_lbl2, branch_lbl2, price_lbl6, price_lbl3, price_lbl7, price_lbl8,
                quan_lbl2, quan_lbl3, quan_lbl6, quan_lbl7, quan_lbl8,
                rest_lbl3, rest_lbl4, rest_lbl7, rest_lbl8, rest_lbl5, date_lbl7, rest_lbl10, date_lbl4,
                total_lbl3, notes_lbl, total_lbl5, total_lbl7, total_lbl8, user_lbl12, total_lbl6,
                view_lbl2, view_lbl3, view_lbl4, view_lbl6, view_lbl7, view_lbl8, view_lbl11, view_lbl12,
                search_lbl2, search_lbl3, search_lbl4, search_lbl6, search_lbl7, search_lbl8, search_lbl11, search_lbl12,
                dept_lbl11, pass_lbl12, mob_lbl6, deptL2, deptL1, inLabel4, inLabel5, inLabel6, inLabel7, type_lbl12, mob_lbl6, mob_lbl5,
                cName_lbl9, cName_lbl10,
                check_lbl1, check_lbl2, check_lbl3, check_lbl4, check_lbl5, check_lbl6, check_lbl7, check_lbl8, check_lbl9, check_lbl10, return_lbl, from_lbl1, to_lbl1, from_lbl2, to_lbl2, hours_lbl,
                branch_lbl2, branch_lbl3, branch_lbl6, branch_lbl7, branch_lbl9, branch_lbl10, branch_lbl12,
                alarm_lbl2, ok_lbl1, ok_lbl2, item_lbl3, payment_lbl, notes_lbl, total_lbl9, id2_lbl9, dept_lbl00, dept_lbl01, order_dept_lbl, return_dept_lbl,
                outLabel9, inLabel9
            }, 0);
            /* changeFontToEnglish(new JLabel[]{type_lbl1, label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14,
                 stock_lbl1, orders_lbl1, sales_lbl1, purchase_lbl1, returns_lbl1, cons_lbl1,
                 add_lbl2, add_lbl3, add_lbl4, add_lbl7, add_lbl8, add_lbl11, add_lbl12,
                 cName_lbl3, cName_lbl4, cName_lbl5, cName_lbl7, cName_lbl8,
                 ch_lbl3, ch_lbl4, ch_lbl5, del_lbl2, del_lbl3, del_lbl4, del_lbl8, del_lbl11, del_lbl12,
                 edit_lbl2, edit_lbl3, edit_lbl4, edit_lbl7, edit_lbl8, edit_lbl11, edit_lbl12,
                 id_lbl2, id_lbl6, id_lbl7, id_lbl8, id_lbl11, mob_lbl3, mob_lbl4, mob_lbl11,
                 name_lbl2, name_lbl3, name_lbl4, name_lbl5, name_lbl6, name_lbl7, name_lbl8, name_lbl11, name_lbl12,
                 paid_lbl3, paid_lbl4, paid_lbl7, paid_lbl8,
                 price_lbl2, price_lbl3, price_lbl4, price_lbl7, price_lbl8,
                 quan_lbl2, quan_lbl3, quan_lbl4, quan_lbl6, quan_lbl7, quan_lbl8,
                 rest_lbl3, rest_lbl4, rest_lbl7, rest_lbl8,
                 total_lbl3, total_lbl4, total_lbl5, total_lbl7, total_lbl8, total_lbl12, user_lbl12,
                 view_lbl2, view_lbl3, view_lbl4, view_lbl6, view_lbl7, view_lbl8, view_lbl11, view_lbl12,
                 search_lbl2, search_lbl3, search_lbl4, search_lbl6, search_lbl7, search_lbl8, search_lbl11, search_lbl12,
                 dept_lbl11, mail_lbl11, pass_lbl12
            }, "Segoe UI Semibold");
             */
        }

    }//GEN-LAST:event_lang_lblMousePressed

    private void pdf_lblMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pdf_lblMousePressed

        if (panalIs == 2) {
            TablePdf(stockTable, new String[]{"ID", "Item", "Quantity", "Price"});
        } else if (panalIs == 3) {
            TablePdf(salesTable, new String[]{"ID", "Item", "Client Name", "Quantity", "Price"});
        } else if (panalIs == 4) {
            TablePdf(purchasesTable, new String[]{"ID", "Item", "Client Name"});
        } else if (panalIs == 5) {
            TablePdf(deptTable, new String[]{"ID", "Client Name", "rest"});
        }
        if (panalIs == 6) {
            TablePdf(consTable, new String[]{"client name", "ID", "quantity"});
        }
        if (panalIs == 7) {
            TablePdf(returningTable, new String[]{"ID", "Item", "Client Name", "quantity"});
        } else if (panalIs == 8) {
            TablePdf(orderTable, new String[]{"ID", "Item", "Client Name", "quantity"});
        } else if (panalIs == 11) {
            TablePdf(clientTable, new String[]{"ID", "Name", "Dept", "Mobile"});
        } else if (panalIs == 12) {
            TablePdf(userTable, new String[]{"name", "password", "Type"});
        } else if (panalIs == 10) {
            TablePdf(inventoryTable1, new String[]{"Item", "Client Name", "Quantity", "Total", "date"});
            TablePdf(inventoryTable2, new String[]{"Item", "Client Name", "Quantity", "Total", "date"});
        }
    }//GEN-LAST:event_pdf_lblMousePressed

    private void eyeLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eyeLabel1MouseClicked

        show(jScrollPane1, jScrollPane5, jScrollPane8, jScrollPane9, jScrollPane11, jScrollPane12);

        String name1 = "unknown", quant1 = null, id1 = null, price1 = null;
        if (homeTable1.getRowCount() != 0) {
            for (int i = homeTable1.getRowCount() - 1; i >= 0; i--) {
                homeTable1.removeRow(i);
            }
        }
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `stock` where quantity != -1 and ID != -1 AND ID != 0";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                name1 = d.rs.getString("Name");
                id1 = String.valueOf(d.rs.getLong("ID"));
                quant1 = String.valueOf(d.rs.getInt("Quantity"));
                price1 = String.valueOf(d.rs.getFloat("Price"));
                String[] rowData = {id1, name1, quant1, price1};
                homeTable1.addRow(rowData);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }


    }//GEN-LAST:event_eyeLabel1MouseClicked

    private void eyeLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eyeLabel2MouseClicked
        show(jScrollPane5, jScrollPane1, jScrollPane8, jScrollPane9, jScrollPane11, jScrollPane12);
        if (homeTable2.getRowCount() != 0) {
            for (int i = homeTable2.getRowCount() - 1; i >= 0; i--) {
                homeTable2.removeRow(i);
            }
        }
        homeTable2.setColumnCount(0);
        String s[] = {"Item", "Client name", "Quantity"};
        homeTable2 = createTableCols(Home1, s);
        String name1 = "unknown", id1 = null, quant1 = null, clientName = "unknown", Item = null;

        DBcon d = new DBcon();
        int count = 0;
        String sql2 = "SELECT * FROM `orders`";
        try {
            d.rs = d.st.executeQuery(sql2);
            while (d.rs.next()) {
                id1 = String.valueOf(d.rs.getInt("ID"));
                quant1 = String.valueOf(d.rs.getInt("Quantity"));
                clientName = d.rs.getString("Client_name");
                Item = d.rs.getString("Name");
                String[] rowData = {Item, clientName, quant1};
                homeTable2.addRow(rowData);
                count++;
            }
            jLabel6.setText(String.valueOf(count));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_eyeLabel2MouseClicked

    private void eyeLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eyeLabel3MouseClicked
        show(jScrollPane8, jScrollPane1, jScrollPane5, jScrollPane9, jScrollPane11, jScrollPane12);
        String name1 = "unknown", quant1 = null, id1 = null, price = null;
        String sql3 = null;

        if (homeTable3.getRowCount() != 0) {
            for (int i = homeTable3.getRowCount() - 1; i >= 0; i--) {
                homeTable3.removeRow(i);
            }
        }
        DBcon d = new DBcon();
        DBcon dd = new DBcon();
        String sql2 = "SELECT * FROM `sales`";
        try {
            String item = null;
            d.rs = d.st.executeQuery(sql2);
            int temp;
            long idd;
            int count = 0;
            while (d.rs.next()) {
                id1 = String.valueOf(d.rs.getInt("check_id"));
                quant1 = String.valueOf(d.rs.getInt("quantity"));
                temp = d.rs.getInt("client_id");
                sql3 = "SELECT clients.Name FROM `clients` WHERE  clients.ID ='" + temp + "';";
                dd.r = dd.st.executeQuery(sql3);
                while (dd.r.next()) {
                    name1 = dd.r.getString("Name");
                }
                //...........
                idd = d.rs.getLong("stock_id");
                sql3 = "SELECT * FROM stock WHERE ID ='" + idd + "';";
                dd.r = dd.st.executeQuery(sql3);
                while (dd.r.next()) {
                    count++;
                    item = dd.r.getString("Name");
                    price = String.valueOf(dd.r.getInt("Price"));
                }
                jLabel7.setText(String.valueOf(count));
                String[] rowData = {item, name1, quant1, price};
                homeTable3.addRow(rowData);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_eyeLabel3MouseClicked

    private void eyeLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eyeLabel4MouseClicked
        show(jScrollPane9, jScrollPane1, jScrollPane5, jScrollPane8, jScrollPane11, jScrollPane12);

        if (homeTable4.getRowCount() != 0) {
            for (int i = homeTable4.getRowCount() - 1; i >= 0; i--) {
                homeTable4.removeRow(i);
            }
        }

        String name1 = "unknown", quant1 = null, id1 = null, price2 = null;
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `outgoings` ,`clients` WHERE  outgoings.client_id = `clients`.ID";
        try {
            String item = null;
            d.rs = d.st.executeQuery(sql2);
            while (d.rs.next()) {
                id1 = String.valueOf(d.rs.getInt("outgoings.check_id"));
                name1 = d.rs.getString("clients.Name");
                item = d.rs.getString("outgoings.stock_name");
                String[] rowData = {id1, item, name1};
                homeTable4.addRow(rowData);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_eyeLabel4MouseClicked

    private void eyeLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eyeLabel5MouseClicked
        show(jScrollPane11, jScrollPane1, jScrollPane5, jScrollPane9, jScrollPane8, jScrollPane12);
        String id1, quant1, sql3, name1 = "unknown", Item = null, stockName = null;
        long stockId = 0;
        if (homeTable5.getRowCount() != 0) {
            for (int i = homeTable5.getRowCount() - 1; i >= 0; i--) {
                homeTable5.removeRow(i);
            }
        }
        int count = 0;
        DBcon db = new DBcon();
        DBcon dd = new DBcon();
        String sql21 = "SELECT * FROM `returns`";
        try {
            db.rs = db.st.executeQuery(sql21);
            int temp;
            while (db.rs.next()) {
                id1 = String.valueOf(db.rs.getInt("id"));
                quant1 = String.valueOf(db.rs.getInt("quantity"));
                temp = db.rs.getInt("client");
                sql3 = "SELECT clients.Name FROM `clients` WHERE  clients.ID ='" + temp + "';";
                dd.r = dd.st.executeQuery(sql3);
                while (dd.r.next()) {
                    name1 = dd.r.getString("Name");
                }
                stockId = db.rs.getLong("name");
                sql3 = "SELECT * FROM `stock` WHERE  ID ='" + stockId + "';";
                dd.r = dd.st.executeQuery(sql3);
                while (dd.r.next()) {
                    stockName = dd.r.getString("Name");
                    count++;
                }
                String[] rowData = {stockName, name1, id1, quant1};
                homeTable5.addRow(rowData);
                jLabel12.setText(String.valueOf(count));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }

    }//GEN-LAST:event_eyeLabel5MouseClicked

    private void eyeLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eyeLabel6MouseClicked

        show(jScrollPane12, jScrollPane1, jScrollPane5, jScrollPane9, jScrollPane11, jScrollPane8);
        //Home.removeColumnSelectionInterval(0, 2);
        if (homeTable6.getRowCount() != 0) {
            for (int i = homeTable6.getRowCount() - 1; i >= 0; i--) {
                homeTable6.removeRow(i);
            }
        }
        String name = "unknown", id, quant;
        int count = 0;
        DBcon d = new DBcon();
        int cons = 0;
        String sql2 = "SELECT * FROM `stock` WHERE quantity != -1";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                cons = d.rs.getInt("cons");
                if (cons > d.rs.getInt("Quantity")) {
                    name = d.rs.getString("Name");
                    id = String.valueOf(d.rs.getLong("ID"));
                    quant = String.valueOf(d.rs.getInt("Quantity"));
                    count++;
                    String[] rowData = {name, quant};
                    homeTable6.addRow(rowData);
                }
            }
            jLabel14.setText(String.valueOf(count));
            //dm.getDataVector();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }


    }//GEN-LAST:event_eyeLabel6MouseClicked

    //DefaultTableModel modle;
    private void add_icon2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon2MouseClicked
        //add stock
        setLabelIcon(add_icon2, "/images/add2.png");
        if (stockTable.getRowCount() != 0) {
            for (int i = stockTable.getRowCount() - 1; i >= 0; i--) {
                stockTable.removeRow(i);
            }
        }

        String name1 = "unknown", id1 = null, quant1 = null, price1 = null;
        String name = "unknown";
        long id;
        int flag2 = 1, stockQuant = 0;
        int quant = 0, cons = 0;
        float price = 0, salesprice = 0;
        name = stock_name.getText();
        int branchId = 0;
        String branch = String.valueOf(stockComboBox.getSelectedItem());

        if (this.stock_id.getText().isEmpty() == true) {
            id = auto_insert("stock", "ID");
        } else {
            id = Long.parseLong(this.stock_id.getText());
        }
        quant = Integer.parseInt(stock_quan.getText());
        price = Float.parseFloat(stockpurchasesPrice.getText());
        cons = Integer.parseInt(alarmRange.getText());
        salesprice = Float.parseFloat(stocksalePrice.getText());;
        DBcon d = new DBcon();
        try {
            String sql4 = "SELECT * FROM `stock`,branch WHERE stock.branch = branch.id AND stock.Name ='" + name + "' AND branch.name = '" + branch + "';";
            d.rs = d.st.executeQuery("select * from branch where name = '" + branch + "';");
            while ((d.rs).next()) {
                branchId = d.rs.getInt("id");
            }
            String sql1 = "SELECT * FROM `stock` ";//branch WHERE stock.branch = branch.id AND stock.Name ='" + name + "' AND branch.name = '" + branch + "';";
            d.rs = d.st.executeQuery(sql1);
            while ((d.rs).next()) {
                if (name.equals(d.rs.getString("stock.Name"))) {
                    flag2 = 0;
                    break;
                } else {
                    flag2++;
                }
            }

            if (flag2 == 0) {
                d.rs = d.st.executeQuery(sql4);
                while ((d.rs).next()) {

                    stockQuant = d.rs.getInt("stock.Quantity");
                }
                if (stockQuant == -1) {
                    stockQuant = 0;
                }
                d.st = d.con.createStatement();
                d.st.executeUpdate("UPDATE `stock` SET `Quantity` = '" + (stockQuant + quant) + "',`Price` = '" + price + "',salesPrice = '" + salesprice + "' WHERE ID = '" + id + "' AND branch = '" + branchId + "';");
            } else {
                stockQuant = quant;
                String sqll = "INSERT INTO `stock` (`ID`, `Name`, `Quantity`,`Price`,`cons`,`salesPrice`,`branch`) VALUES ('" + id + "', '" + name + "','" + quant + "','" + price + "', '" + cons + "' , '" + salesprice + "', '" + branchId + "');";
                d.st = d.con.createStatement();
                d.st.executeUpdate(sqll);
            }
            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `purchases` (`check_id`, `Total`, `Paid`, `client_id`, `stock_id`, `quantity`, `Rest`,`date`,`user`) VALUES ('" + auto_insert("purchases", "check_id") + "', '" + quant * price + "', '" + quant * price + "', '" + 0 + "', '" + id + "', '" + quant + "', '" + 0 + "', '" + LocalDate.now() + "' , '" + getUser() + "');");
//             d.rs = d.st.executeQuery("select * from rebellions");
//                d.rs.last();
//                if(d.rs.getBoolean("start")){
//                float oldTotal = 0; 
//                oldTotal = d.rs.getFloat("total");
//                d.st = d.con.createStatement();
//                d.st.executeUpdate("UPDATE `rebellions` SET `total` = '" +(-(quant * price) + oldTotal) + "';");
//                }
//        try {
//            name = stock_name.getText();
//            int branchId = 0;
//            id = Integer.parseInt(this.stock_id.getText());
//            quant = Integer.parseInt(stock_quan.getText());
//            price = Float.parseFloat(stockpurchasesPrice.getText());
//            cons = Integer.parseInt(alarmRange.getText());
//            salesprice = Float.parseFloat(stocksalePrice.getText());;
//            DBcon d = new DBcon();
//            String sql = "INSERT INTO `stock` (`ID`, `Name`, `Quantity`, `Price`, `cons` ,`salesPrice`,`branch`) VALUES (" + id + ",'" + name + "', " + quant + ", '" + price + "', '" + cons + "', '" + salesprice + "', '" + branchId + "');";
//            d.st = d.con.createStatement();
//            d.st.executeUpdate(sql);
            viewStock();
            stock_id.setText(id + "");
            JOptionPane.showMessageDialog(null, "Done   --  تم");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_add_icon2MouseClicked

    private void delete_icon2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_icon2MouseClicked
        //delete

        delete_icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete2.png")));

        int dialogResult = JOptionPane.showConfirmDialog(null, "delete ? -- مسح ؟", "Warning -- تحذير", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            if (stockTable.getRowCount() != 0) {
                for (int i = stockTable.getRowCount() - 1; i >= 0; i--) {
                    stockTable.removeRow(i);
                }
            }
            String name1 = "unknown", id1 = null, quant1 = null, price1 = null;
            long id;
            try {
                id = Long.parseLong(stock_id.getText());
                DBcon d = new DBcon();
                String sql = "update `stock` set quantity = -1 WHERE stock.ID = " + id + " and ID != -1;";
                d.st = d.con.createStatement();
                d.st.executeUpdate(sql);
                String sql2 = "SELECT * FROM `stock` where quantity != -1";
                d.rs = d.st.executeQuery(sql2);
                JOptionPane.showMessageDialog(null, "Done   --  تم");
                viewStock();

                //dm.getDataVector();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }

        } else {
            JOptionPane.showMessageDialog(null, "OK");
        }

    }//GEN-LAST:event_delete_icon2MouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        //select stock
        String id = null, name = "unknown", quant = null, price = null;
        int n = jTable2.getSelectedRow();
        name = stockTable.getValueAt(n, 1).toString();
        id = stockTable.getValueAt(n, 0).toString();
        quant = stockTable.getValueAt(n, 2).toString();
        price = stockTable.getValueAt(n, 3).toString();
        String branch = null;
        int alarm = 0;
        String cons = null;
        DBcon d = new DBcon();
        float salesprice = 0;
        String sql2 = "SELECT * FROM `stock` WHERE ID = " + id + ";";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                salesprice = d.rs.getFloat("salesPrice");
                cons = String.valueOf(d.rs.getInt("cons"));
                branch = d.rs.getString("branch");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
        stock_id.setText(id);
        stock_name.setText(name);
        stock_quan.setText(quant);
        stockpurchasesPrice.setText(price);
        stocksalePrice.setText(String.valueOf(salesprice));
        alarmRange.setText(cons);
        stockComboBox.setSelectedItem(branch);
    }//GEN-LAST:event_jTable2MouseClicked

    private void view_icon2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon2MouseClicked
        //view 
        view_icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));
        viewStock();
    }//GEN-LAST:event_view_icon2MouseClicked

    private void edit_icon2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_icon2MouseClicked
        //edit

        edit_icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit2.png")));

        if (stockTable.getRowCount() != 0) {
            for (int i = stockTable.getRowCount() - 1; i >= 0; i--) {
                stockTable.removeRow(i);
            }
        }
        String name1 = "unknown", id1 = null, quant1 = null, price1 = null;
        String name = "unknown";
        long id;
        int flag2 = 1, stockQuant = 0;
        int quant = 0, newquant = 0, cons;

        float price = 0, salesprice = 0, newPrice, oldprice;
        name = stock_name.getText();
        int branchId = 0;
        String branch = String.valueOf(stockComboBox.getSelectedItem());

        if (this.stock_id.getText().isEmpty() == true) {
            id = auto_insert("stock", "ID");
        } else {
            id = Long.parseLong(this.stock_id.getText());
        }
        quant = Integer.parseInt(stock_quan.getText());
        price = Float.parseFloat(stockpurchasesPrice.getText());
        cons = Integer.parseInt(alarmRange.getText());
        salesprice = Float.parseFloat(stocksalePrice.getText());;

        DBcon d = new DBcon();
        try {
            String sql4 = "SELECT * FROM `stock`,branch WHERE stock.branch = branch.id AND stock.Name ='" + name + "' AND branch.name = '" + branch + "';";
            d.rs = d.st.executeQuery("select * from branch where name = '" + branch + "';");
            while ((d.rs).next()) {
                branchId = d.rs.getInt("id");
            }
            String sql1 = "SELECT * FROM `stock` ";//,branch WHERE stock.branch = branch.id AND stock.Name ='" + name + "' AND branch.name = '" + branch + "';";
            d.rs = d.st.executeQuery(sql1);
            while ((d.rs).next()) {
                if (name.equals(d.rs.getString("stock.Name"))) {
                    flag2 = 0;
                    break;
                } else {
                    flag2++;
                }
            }
            if (flag2 == 0) {
                d.rs = d.st.executeQuery(sql4);
                while ((d.rs).next()) {

                    stockQuant = d.rs.getInt("stock.Quantity");
                    oldprice = d.rs.getFloat("stock.Price");
                }
                if (stockQuant == -1) {
                    stockQuant = 0;
                }
                d.st = d.con.createStatement();
                d.st.executeUpdate("UPDATE `stock` SET `Quantity` = '" + (quant) + "',`Price` = '" + price + "',salesPrice = '" + salesprice + "',cons = '" + cons + "' WHERE ID = '" + id + "' AND branch = '" + branchId + "';");
            } else {
                JOptionPane.showMessageDialog(null, "not found");
//                stockQuant = quant;
//                String sqll = "INSERT INTO `stock` (`ID`, `Name`, `Quantity`,`Price`,`cons`,`salesPrice`,`branch`) VALUES ('" + id + "', '" + name + "','" + quant + "','" + price + "', '" + cons + "' , '" + salesprice + "', '" + branchId + "');";
//                d.st = d.con.createStatement();
//                d.st.executeUpdate(sqll);
            }
            newquant = quant - stockQuant;
            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `purchases` (`check_id`, `Total`, `Paid`, `client_id`, `stock_id`, `quantity`, `Rest`,`date`,`user`) VALUES ('" + auto_insert("purchases", "check_id") + "', '" + newquant * price + "', '" + newquant * price + "', '" + -1 + "', '" + id + "', '" + newquant + "', '" + 0 + "', '" + LocalDate.now() + "' , '" + getUser() + "');");

//        try {
//            String sql = "UPDATE `stock` SET `Name` = '" + name + "',`Quantity`=" + quant + ",`Price`= " + price + ",`cons` = " + cons + ",`salesPrice` = " + salesprice + " ,`branch` = " + branchId + "   WHERE `ID` = " + id + " ;";
//            d.st = d.con.createStatement();
//            d.st.executeUpdate(sql);
            viewStock();
            JOptionPane.showMessageDialog(null, "Done   --  تم");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }


    }//GEN-LAST:event_edit_icon2MouseClicked

    private void clear_icon2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon2MouseClicked

        clear_icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear2.png")));

        stock_id.setText(null);
        stock_name.setText(null);
        stock_quan.setText(null);
        stockpurchasesPrice.setText(null);
        stocksalePrice.setText(null);
        alarmRange.setText("10");
    }//GEN-LAST:event_clear_icon2MouseClicked

    private void view_icon3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon3MouseClicked
        //sales veiw
        view_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));
        viewSales();
        ////
    }//GEN-LAST:event_view_icon3MouseClicked

    private void view_icon11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon11MouseClicked
        //view clients
        view_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));
        viewClients();
    }//GEN-LAST:event_view_icon11MouseClicked

    private void view_icon4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon4MouseClicked

        // view purchases
        view_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));
        viewPayment();
    }//GEN-LAST:event_view_icon4MouseClicked

    private void view_icon2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon2MouseEntered
        view_icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewG.png")));
    }//GEN-LAST:event_view_icon2MouseEntered

    private void view_icon2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon2MouseExited
        view_icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewD.png")));
    }//GEN-LAST:event_view_icon2MouseExited

    private void stock_nameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stock_nameKeyTyped
        /*if (evt.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
            hidePlaceHolder(name_place2);
        } else {
            if (stock_name.getText().equals("")) {
                showPlaceHolder(name_place2, "Ex : Mercedez benz");
            }
        }
         */
        //............
    }//GEN-LAST:event_stock_nameKeyTyped

    private void stock_quanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stock_quanKeyTyped
        /* if (evt.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
            hidePlaceHolder(quan_place2);
        } else {
            if (stock_quan.getText().equals("")) {
                showPlaceHolder(quan_place2, "Ex : 1000");
            }
        }
         */
    }//GEN-LAST:event_stock_quanKeyTyped

    private void stockpurchasesPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stockpurchasesPriceKeyTyped
        /* if (evt.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
            hidePlaceHolder(price_place2);
        } else {
            if (stock_price.getText().equals("")) {
                showPlaceHolder(price_place2, "Ex : 1000000");
            }
        }
         */
    }//GEN-LAST:event_stockpurchasesPriceKeyTyped

    private void videoPlay1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_videoPlay1MouseClicked

//        try {
//            Desktop.getDesktop().open(new File("C:\\Users\\ahmed\\OneDrive\\Documents\\NetBeansProjects\\new_store\\src\\videos\\vid1.mp4"));
//        } catch (IOException ex) {
//            JOptionPane.showMessageDialog(null, "Can't open video");
//        }

    }//GEN-LAST:event_videoPlay1MouseClicked

    private void add_icon3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon3MouseClicked

        add_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add2.png")));

        //add sales
        if (salesTable.getRowCount() != 0) {
            for (int i = salesTable.getRowCount() - 1; i >= 0; i--) {
                salesTable.removeRow(i);
            }
        }
        String name = "unknown", client_name = "unknown", mobile = "unknown";
        int check_id = 0, quant = 0;
        float paid = 0, oldDept = 0;
        float price = 0;
        try {
            name = salesStockName.getText();

            if (salesCheckId.getText().isEmpty() == true) {
                check_id = auto_insert("sales", "check_id");
            } else {
                check_id = Integer.valueOf(salesCheckId.getText());
            }

            if (salesClient.getText().equals("")) {
                client_name = "unknown";
                salesClient.setText(client_name);
            } else {
                client_name = salesClient.getText();
            }

            if (salesMob.getText().equals("")) {
                mobile = "unknown";
                salesMob.setText(mobile);
            } else {
                mobile = salesMob.getText();
            }

            mobile = salesMob.getText();
            quant = Integer.parseInt(salesQuant.getText());
            paid = Float.parseFloat(salespaid.getText());
            String branch = String.valueOf(salesComboBox.getSelectedItem());
            price = Float.valueOf(salesPrice.getText());

            long stock_id = 0;
            float rest = 0;
            float total = 0;
            float dept = 0;
            int id = 0;

            int flag = 1;
            int stockQuant = 0;
            int branchId = 0;
            LocalDate time = LocalDate.now();
            DBcon d = new DBcon();
            String sql4 = "SELECT * FROM `stock`,branch WHERE stock.branch = branch.id AND stock.Name ='" + name + "' AND branch.name = '" + branch + "';";
            d.rs = d.st.executeQuery(sql4);
            while ((d.rs).next()) {
                stock_id = d.rs.getLong("stock.ID");
                stockQuant = d.rs.getInt("stock.Quantity");
                branchId = d.rs.getInt("branch.id");
            }
            salesPrice.setText(String.valueOf(price));
            total = (float) quant * price;
            salestotal.setText(String.valueOf(total));
            rest = total - paid;
            salesRest.setText(String.valueOf(rest));
            if (deptBox1.isSelected() == false) {
                rest = 0;
            }

            if (stockQuant >= quant || salesStockId.getText().equals("0")) {

                // d.rs.close();
                d.rs = d.st.executeQuery("SELECT * FROM `clients`");
                while ((d.rs).next()) {
                    if ((d.rs.getString("Name")).equals(client_name)) {
                        flag = 0;
                        break;
                    } else {
                        flag++;
                        continue;
                    }
                }
                dept = rest;
                if (flag != 0) {

                    id = auto_insert("clients", "ID");
                } else {
                    d.rs = d.st.executeQuery("SELECT * FROM `clients` WHERE `clients`.`Name`='" + client_name + "';");
                    while (d.rs.next()) {
                        id = d.rs.getInt("ID");
                        oldDept = d.rs.getFloat("dept");
                    }
                }

                String sql2 = "INSERT INTO `sales` (`check_id`, `Total`, `Paid`, `client_id`, `stock_id`, `quantity`, `Rest`,`date`,`user`) VALUES ('" + check_id + "', '" + total + "', '" + paid + "', '" + id + "', '" + stock_id + "', '" + quant + "', '" + rest + "', '" + time + "' , '" + getUser() + "');";
                String sql3;
                if (flag != 0) {

                    sql3 = "INSERT INTO `clients` (`ID`,`Name`, `Email`, `Mobile`, `dept`,`date`) VALUES ('" + id + "','" + client_name + "','unknown','" + mobile + "','" + dept + "','" + time + "');";
                    d.st = d.con.createStatement();
                    d.st.executeUpdate(sql3);

                } else {
                    String ss = "UPDATE `clients` SET `dept`= '" + (oldDept + dept) + "' WHERE `ID` = " + id + ";";
                    d.st = d.con.createStatement();
                    d.st.executeUpdate(ss);
                }

                d.st = d.con.createStatement();
                d.st.executeUpdate(sql2);
                String ss = "UPDATE `stock` SET `Quantity`= '" + (stockQuant - quant) + "' WHERE `ID` = " + stock_id + " AND branch = " + branchId + " AND ID != '0' ;";
                d.st = d.con.createStatement();
                d.st.executeUpdate(ss);
                d.rs = d.st.executeQuery("select * from rebellions");
                d.rs.last();
                if (d.rs.getBoolean("start")) {
                    float oldTotal = 0;
                    oldTotal = d.rs.getFloat("total");
                    float total1 = oldTotal + total;
                    d.st = d.con.createStatement();
                    d.st.executeUpdate("UPDATE `rebellions` SET `total` = '" + total1 + "';");
                    d.st = d.con.createStatement();
                    d.st.executeUpdate("INSERT INTO `rebelliontable` (`id`, `item`, `total`) VALUES ('" + check_id + "', '" + name + "', '" + total + "');");
                }
                d.rs = d.st.executeQuery("select * from day");
                d.rs.last();
                if (d.rs.getBoolean("start")) {
                    float oldTotal = 0;
                    oldTotal = d.rs.getFloat("total");
                    float total1 = oldTotal + total;
                    d.st = d.con.createStatement();
                    d.st.executeUpdate("UPDATE `day` SET `total` = '" + total1 + "';");
                    d.st = d.con.createStatement();
                    d.st.executeUpdate("INSERT INTO `daytable` (`id`, `item`, `total`) VALUES ('" + check_id + "', '" + name + "', '" + total + "');");
                }
                JOptionPane.showMessageDialog(null, "Done   --  تم");
            } else {
                JOptionPane.showMessageDialog(null, "quantity not enaugh -- الكمية غير كافية");
            }
            //  view_icon3.action("mouseClick", d)

            viewSales();
            this.salesCheckId.setText(check_id + "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }

    }//GEN-LAST:event_add_icon3MouseClicked

    private void add_icon11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon11MouseClicked

        add_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add2.png")));
        try {
            //add clients
            if (clientTable.getRowCount() != 0) {
                for (int i = clientTable.getRowCount() - 1; i >= 0; i--) {
                    clientTable.removeRow(i);
                }
            }
            String name1 = "unknown", id1 = null, dept1 = null, email1, mob1;
            // 
            int id = auto_insert("clients", "ID");
            String name = jTextField64.getText();
            String mobile = jTextField83.getText();
            float dept = Float.parseFloat(jTextField70.getText());
            DBcon d = new DBcon();
            if (jTextField61.getText().isEmpty()) {
                String sql = "INSERT INTO `clients` (`ID`, `Name`, `Email`, `Mobile`, `dept`, `date`) VALUES ('" + id + "', '" + name + "', ' null ', " + mobile + ", " + dept + ",'0000-00-00');";
                d.st = d.con.createStatement();
                d.st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Done   --  تم");
            } else {
                JOptionPane.showMessageDialog(null, "Clear Fields And Try Again -- امسح البيانات و حاول مرة اخري");
            }
            viewClients();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_add_icon11MouseClicked

    private void delete_icon11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_icon11MouseClicked

        delete_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete2.png")));
        try {
            //delete clients
            if (clientTable.getRowCount() != 0) {
                for (int i = clientTable.getRowCount() - 1; i >= 0; i--) {
                    clientTable.removeRow(i);
                }
            }
            String name1 = "unknown", id1 = null, dept1 = null, email1, mob1;
            // 
            int id = Integer.parseInt(jTextField61.getText());

            DBcon d = new DBcon();
            String sql = "DELETE FROM `clients` WHERE ID =" + id + ";";

            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Done   --  تم");
            viewClients();
            //dm.getDataVector();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }

    }//GEN-LAST:event_delete_icon11MouseClicked

    private void clientTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clientTMouseClicked
        //select clients row
        String id = null, name = "unknown", mobile = "unknown", dept = null;
        int n = clientT.getSelectedRow();

        DBcon d = new DBcon();
        name = clientTable.getValueAt(n, 1).toString();
        id = clientTable.getValueAt(n, 0).toString();
        String sql = "SELECT * FROM clients WHERE ID = " + clientTable.getValueAt(n, 0) + ";";
        try {
            d.rs = d.st.executeQuery(sql);
            while ((d.rs).next()) {
                mobile = d.rs.getString("Mobile");
                dept = String.valueOf(d.rs.getFloat("dept"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
        jTextField61.setText(id);
        jTextField64.setText(name);
        jTextField83.setText(mobile);
        jTextField70.setText(dept);
    }//GEN-LAST:event_clientTMouseClicked

    private void edit_icon11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_icon11MouseClicked

        edit_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit2.png")));
        try {
            //clients edit
            if (clientTable.getRowCount() != 0) {
                for (int i = clientTable.getRowCount() - 1; i >= 0; i--) {
                    clientTable.removeRow(i);
                }
            }
            String name1 = "unknown", id1 = null, dept1 = null, email1, mob1;

            int id = Integer.parseInt(jTextField61.getText());
            String name = jTextField64.getText();
            String mobile = jTextField83.getText();

            float dept = Float.parseFloat(jTextField70.getText());
            DBcon d = new DBcon();
            String sql = "UPDATE `clients` SET `ID` = '" + id + "', `Name` = '" + name + "', `Email` = 'null', `Mobile` = '" + mobile + "', `dept` = '" + dept + "' WHERE `clients`.`ID` = " + id + ";";
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Done   --  تم");
            viewClients();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_edit_icon11MouseClicked

    private void clear_icon11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon11MouseClicked

        clear_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear2.png")));
        jTextField61.setText(null);
        jTextField64.setText(null);
        jTextField83.setText(null);
        jTextField70.setText(null);
    }//GEN-LAST:event_clear_icon11MouseClicked

    private void edit_icon3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_icon3MouseClicked
        //edit sales

        edit_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit2.png")));

        try {
            if (salesTable.getRowCount() != 0) {
                for (int i = salesTable.getRowCount() - 1; i >= 0; i--) {
                    salesTable.removeRow(i);
                }
            }

            String name = "unknown", client_name = "unknown", mobile = "unknown";
            int check_id = 0, quant = 0;
            float total = 0, paid = 0, price = 0;
            name = salesStockName.getText();
            check_id = Integer.parseInt(salesCheckId.getText());
            client_name = salesClient.getText();
            mobile = salesMob.getText();
            quant = Integer.parseInt(salesQuant.getText());
            price = Float.parseFloat(salesPrice.getText());
            total = (float) quant * price;
            paid = Float.parseFloat(salespaid.getText());
            String branch = String.valueOf(salesComboBox.getSelectedItem());
            int branchID = 0;
            float rest = total - paid;
            salesRest.setText(String.valueOf(rest));
            long stock_id = 0;
            int stockQuant = 0;
            int old;
            int wrong = 0;
            DBcon d = new DBcon();

            String sql2 = "SELECT * FROM `stock`,branch WHERE stock.branch = branch.ID AND branch = '" + branch + "' AND stock.Name='" + name + "';";
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                stockQuant = d.rs.getInt("stock.Quantity");
                branchID = d.rs.getInt("branch.id");
            }
            String sql3 = "SELECT * FROM `sales` WHERE sales.check_id='" + check_id + "';";
            d.rs = d.st.executeQuery(sql3);
            while ((d.rs).next()) {

                wrong = d.rs.getInt("quantity");
            }
            old = wrong + stockQuant;
            String sql4 = "SELECT * FROM `stock` WHERE stock.Name='" + name + "';";
            d.rs = d.st.executeQuery(sql4);
            while ((d.rs).next()) {
                stock_id = d.rs.getLong("ID");
            }
            String sql6 = "UPDATE `stock` SET `Quantity` = '" + (old - quant) + "' WHERE `stock`.`ID` = " + stock_id + " AND branch = '" + branchID + "';";
            String sql = "UPDATE `sales` SET `check_id` = '" + check_id + "', `Paid` = '" + paid + "',  `stock_id` = '" + stock_id + "', `quantity` = '" + quant + "' WHERE `sales`.`check_id` = '" + check_id + "';";
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql6);
            JOptionPane.showMessageDialog(null, "Done   --  تم");
            viewSales();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_edit_icon3MouseClicked

    private void clear_icon3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon3MouseClicked

        clear_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear2.png")));
        salesCheckId.setText(null);
        salesClient.setText(null);
        salesMob.setText(null);
        salesStockName.setText(null);
        salesQuant.setText(null);
        salesPrice.setText(null);
        salestotal.setText(null);
        salespaid.setText(null);
        salesRest.setText(null);
        salesStockId.setText(null);
        deptBox1.setSelected(false);
    }//GEN-LAST:event_clear_icon3MouseClicked

    private void add_icon4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon4MouseClicked

        add_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add2.png")));
        float rest = 0;
        try {
            //add purchase
            if (purchasesTable.getRowCount() != 0) {
                for (int i = purchasesTable.getRowCount() - 1; i >= 0; i--) {
                    purchasesTable.removeRow(i);
                }
            }
            String client_name, mobile;
            String name = namePayment.getText();
            int check_id;
            if (checkIdPayment.getText().isEmpty() == true) {
                check_id = auto_insert("outgoings", "check_id");
            } else {
                check_id = Integer.valueOf(checkIdPayment.getText());
            }
            if (clientNamePayment.getText().equals("")) {
                clientNamePayment.setText("unknown");
            }
            if (mobilePayment.getText().equals("")) {
                mobilePayment.setText("unknown");
            }
            client_name = clientNamePayment.getText();
            mobile = mobilePayment.getText();
            float total = Float.valueOf(totalPayment.getText());
            float paid = Float.parseFloat(paidPayment.getText());
            long stock_id = 0;
            rest = total - paid;
            float dept = 0;
            int id = 0;
            int flag = 1;
            int prof = 0;
            float salesPrice = 0;
            String notes = this.notes.getText();
            float oldDept = 0;
            LocalDate time = LocalDate.now();
            restPayment.setText(String.valueOf(rest));
            if (deptBox2.isSelected() == false) {
                rest = 0;
            }
            DBcon d = new DBcon();
            String sql = "SELECT * FROM `clients`";
//..........................................................................................................................
            d.st = d.con.createStatement();
            d.rs = d.st.executeQuery(sql);
            while ((d.rs).next()) {
                if ((d.rs.getString("clients.Name")).equals(client_name)) {
                    flag = 0;
                    break;
                } else {
                    flag++;
                    continue;
                }
            }
            if (flag != 0) {
                dept = rest;
                id = auto_insert("clients", "ID");
            } else {
                d.rs = d.st.executeQuery("SELECT * FROM `clients` WHERE `clients`.`Name`='" + client_name + "';");
                while (d.rs.next()) {
                    id = d.rs.getInt("ID");
                    oldDept = d.rs.getFloat("dept");
                }
            }
            String sql3 = "INSERT INTO `clients` (`ID`,`Name`, `Email`, `Mobile`, `dept`,`date`) VALUES ('" + id + "','" + client_name + "','unknown','" + mobile + "','" + (-dept) + "','" + time + "');";
            if (flag != 0) {
                d.st = d.con.createStatement();
                d.st.executeUpdate(sql3);
            } else {
                String ss = "UPDATE `clients` SET `dept`= '" + (oldDept + (-dept)) + "' WHERE `ID` = '" + id + "';";
                d.st = d.con.createStatement();
                d.st.executeUpdate(ss);
            }
            if (paymentBox1.isSelected() == true) {
                prof = 1;
            } else {
                prof = 0;
            }
            String sql2 = "INSERT INTO `outgoings` (`check_id`, `stock_name`, `client_id`, `total`, `paid`, `rest`, `note`, `inProfits`, `date`,`user`) VALUES ('" + check_id + "','" + name + "', '" + id + "' , '" + total + "', '" + paid + "', '" + rest + "' , '" + notes + "' , '" + prof + "' , '" + LocalDate.now() + "','" + getUser() + "');";
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql2);
            JOptionPane.showMessageDialog(null, "Done   --  تم");
            String name1 = "unknown", id1 = null;
            viewPayment();
            this.checkIdPayment.setText(check_id + "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري" + e);
        }

//        float paid = Float.parseFloat(paidPurchase.getText());
//        float total = Float.parseFloat(totalPurchase.getText());
//        float rest = total - paid;
        restPayment.setText(String.valueOf(rest));
    }//GEN-LAST:event_add_icon4MouseClicked

    private void paymentTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paymentTMouseClicked
        //select purchases

        String id = null, name1 = "unknown", email = "unknown", mobile = "unknown";

        String check_id;
        String client_name;
        //float salesprice =0;

        float total = 0;
        float paid = 0;
        float rest = 0;
        long stock_id = 0;
        String date = null;
        String Item = null;
        String user = null;
        String branch = null;
        float salesPrice = 0;
        String notes = null;
        String clientName = null;
        int n = paymentT.getSelectedRow();
        boolean isProfits = false;
        DBcon d = new DBcon();
        check_id = purchasesTable.getValueAt(n, 0).toString();
        Item = purchasesTable.getValueAt(n, 1).toString();
        client_name = purchasesTable.getValueAt(n, 2).toString();
        Idflag = Integer.parseInt(check_id);
        clientNamePayment.setText(client_name);
        checkIdPayment.setText(check_id);
        namePayment.setText(Item);

        String sql2 = "SELECT * FROM outgoings,clients WHERE check_id = " + check_id + ";";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                total = d.rs.getFloat("total");
                paid = d.rs.getFloat("paid");
                rest = d.rs.getFloat("rest");
                user = d.rs.getString("user");
                isProfits = d.rs.getBoolean("inProfits");
                date = String.valueOf(d.rs.getDate("date"));
                //stock_id = d.rs.getLong("stock_id");
                mobile = String.valueOf(d.rs.getString("Mobile"));
                notes = d.rs.getString("note");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري" + ex);
        }
        checkIdPayment.setText(check_id);
        clientNamePayment.setText(client_name);
        mobilePayment.setText(mobile);
        namePayment.setText(Item);
        totalPayment.setText(String.valueOf(total));
        paidPayment.setText(String.valueOf(paid));
        restPayment.setText(String.valueOf(rest));
        UserPayment.setText(user);
        paymentDate.setText(date);
        paymentBox1.setSelected(isProfits);
        this.notes.setText(notes);

        // }
    }//GEN-LAST:event_paymentTMouseClicked

    private void myDeptEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_myDeptEMouseClicked
        //mydept
        x = 1;

        if (deptTable.getRowCount() != 0) {
            for (int i = deptTable.getRowCount() - 1; i >= 0; i--) {
                deptTable.removeRow(i);
            }
        }
        DBcon d = new DBcon();
        float dept = 0;
        String name = "unknown";
        int id = 0;
        float rest = 0;
        float paid;
        String sql = "SELECT * FROM outgoings,clients WHERE rest != 0 AND outgoings.client_id = clients.ID;";
        try {
            d.rs = d.st.executeQuery(sql);
            while (d.rs.next()) {
                paid = d.rs.getFloat("paid");
                rest = d.rs.getFloat("rest");
                dept += d.rs.getFloat("rest");
                id = d.rs.getInt("check_id");
                name = d.rs.getString("clients.Name");
                String[] rowData = {String.valueOf(id), name, String.valueOf(rest), String.valueOf(paid)};
                deptTable.addRow(rowData);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        myDeptL.setText(String.valueOf(dept));
    }//GEN-LAST:event_myDeptEMouseClicked

    private void deptMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deptMouseClicked
        //dept
        x = 2;

        if (deptTable.getRowCount() != 0) {
            for (int i = deptTable.getRowCount() - 1; i >= 0; i--) {
                deptTable.removeRow(i);
            }
        }
        DBcon d = new DBcon();
        float dept = 0;
        String name = "unknown";
        int id = 0;
        float rest = 0;
        int temp = 0;
        float paid = 0;
        String sql = "SELECT * FROM sales,clients WHERE sales.client_id = clients.ID AND Rest != 0;";

        try {
            d.rs = d.st.executeQuery(sql);
            while (d.rs.next()) {
                paid = d.rs.getFloat("sales.Paid");
                rest = d.rs.getFloat("sales.Rest");
                dept += d.rs.getFloat("sales.Rest");
                temp = d.rs.getInt("sales.client_id");
                id = d.rs.getInt("sales.check_id");
                name = d.rs.getString("clients.Name");

                String[] rowData = {String.valueOf(id), name, String.valueOf(rest), String.valueOf(paid)};
                deptTable.addRow(rowData);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Home.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        deptL.setText(String.valueOf(dept));

    }//GEN-LAST:event_deptMouseClicked

    private void deptTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deptTMouseClicked
        //dept select
        int n = deptT.getSelectedRow();

        DBcon d = new DBcon();
        DBcon db = new DBcon();
        String name = deptTable.getValueAt(n, 1).toString();
        String check_id = deptTable.getValueAt(n, 0).toString();
        String rest = deptTable.getValueAt(n, 2).toString();
        String paid = deptTable.getValueAt(n, 3).toString();
        long stock_id = 0;
        String stock_name = null;
        String sql = null;
        if (x == 1) {
            sql = "SELECT * FROM outgoings WHERE check_id = " + check_id + ";";
            String sql2;
            try {
                d.rs = d.st.executeQuery(sql);
                while (d.rs.next()) {

                    stock_name = d.rs.getString("stock_name");

                }
            } catch (SQLException ex) {
                Logger.getLogger(Home.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        } else if (x == 2) {
            sql = "SELECT * FROM sales WHERE check_id = " + check_id + ";";
            String sql2;
            try {
                d.rs = d.st.executeQuery(sql);
                while (d.rs.next()) {
                    stock_id = d.rs.getLong("stock_id");
                    sql2 = "select Name FROM stock where ID = " + stock_id + ";";
                    db.rs = db.st.executeQuery(sql2);
                    while (db.rs.next()) {
                        stock_name = db.rs.getString("Name");

                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Home.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }

        this.check_id.setText(check_id);
        this.client_name.setText(name);
        Name.setText(stock_name);
        Total.setText(rest);
        deptPaid.setText(paid);
    }//GEN-LAST:event_deptTMouseClicked

    private void view_icon6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon6MouseClicked
        view_icon6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));
        viewCons();
    }//GEN-LAST:event_view_icon6MouseClicked

    private void consTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_consTMouseClicked
        //
        String id = null, name = "unknown", quant = null;
        int n = consT.getSelectedRow();
        DBcon d = new DBcon();
        name = consTable.getValueAt(n, 0).toString();
        id = consTable.getValueAt(n, 1).toString();
        quant = consTable.getValueAt(n, 2).toString();
        String branch = null;

        try {

            d.rs = d.st.executeQuery("SELECT * FROM stock ,branch WHERE stock.branch = branch.id AND stock.ID = '" + id + "';");
            while ((d.rs).next()) {
                branch = d.rs.getString("branch.name");

            }
        } catch (SQLException ex) {
            Logger.getLogger(Home.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        consId.setText(id);
        consName.setText(name);
        consQuantity.setText(quant);
        consComboBox.setSelectedItem(branch);
    }//GEN-LAST:event_consTMouseClicked

    private void add_icon8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon8MouseClicked

        add_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add2.png")));
        try {
            //add order
            if (orderTable.getRowCount() != 0) {
                for (int i = orderTable.getRowCount() - 1; i >= 0; i--) {
                    orderTable.removeRow(i);
                }
            }
            String name1 = "unknown", id1 = null, quant1 = null;
            String name = orderName.getText();
            int check_id;
            if (orderId.getText().isEmpty() == true) {
                check_id = auto_insert("orders", "ID");
            } else {
                check_id = Integer.valueOf(orderId.getText());
            }
            if (orderClient.getText().equals("")) {
                orderClient.setText("unknown");
            }
            if (orderQuantity.getText().equals("")) {
                orderQuantity.setText("1");
            }
            if (orderPrice.getText().equals("")) {
                orderPrice.setText("0");
            }
            if (orderTotal.getText().equals("")) {
                orderTotal.setText("0");
            }
            if (orderPaid.getText().equals("")) {
                orderPaid.setText("0");
            }

            String client_name = orderClient.getText();
            int quant = Integer.parseInt(orderQuantity.getText());
            float price = Float.parseFloat(orderPrice.getText());
            float total = (float) quant * price;
            float paid = Float.parseFloat(orderPaid.getText());
            String stock_id = "";
            float dept = 0;
            float rest = total - paid;
            int id = 0;
            int flag = 1;
            int stockQuant = 0;

            orderTotal.setText(String.valueOf(total));
            orderRest.setText(String.valueOf(rest));
            if (deptBox4.isSelected() == false) {
                rest = 0;
            }
            DBcon d = new DBcon();
            String sql = "INSERT INTO `orders` (`ID`, `Client_name`, `Name`, `Quantity`, `Price`, `Total`, `Paid`, `Rest`, `Date`) VALUES ('" + check_id + "', '" + client_name + "', '" + name + "', '" + quant + "','" + price + "', '" + total + "', '" + paid + "', '" + rest + "', '" + LocalDate.now() + "');";

            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Done   --  تم");
            String sql2 = "SELECT * FROM `orders`";

            viewOrders();

            /*d.rs = d.st.executeQuery("SELECT * FROM `clients`");
                    while ((d.rs).next()) {
                        if ((d.rs.getString("Name")).equals(client_name)) {
                            flag = 0;
                            break;
                        } else {
                            flag++;
                            continue;
                        }
                    }
                    if (flag != 0) {
                        dept = rest;
                        id = auto_insert("clients", "ID");
                    } else {
                        d.rs = d.st.executeQuery("SELECT `clients`.`ID` FROM `clients` WHERE `clients`.`Name`='" + client_name + "';");
                        while (d.rs.next()) {
                            id = d.rs.getInt("ID");
                        }
                    }
                    
                    String sql2 = "INSERT INTO `sales` (`check_id`, `Total`, `Paid`, `client_id`, `stock_id`, `quantity`, `Rest`,`date`,`user`) VALUES ('" + check_id + "', '" + total + "', '" + paid + "', '" + id + "', '" + stock_id + "', '" + quant + "', '" + rest + "', '" + time + "' , '" + getUser() + "');";
                    String sql3;
                    if (flag != 0) {
                        
                        sql3 = "INSERT INTO `clients` (`ID`,`Name`, `Email`, `Mobile`, `dept`,`date`) VALUES ('" + id + "','" + client_name + "','null','" + mobile + "','" + dept + "','" + time + "');";
                        
                        d.st = d.con.createStatement();
                        d.st.executeUpdate(sql3);
                        
            }
             */
            orderId.setText(check_id + "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري" + e);
        }

    }//GEN-LAST:event_add_icon8MouseClicked

    private void view_icon8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon8MouseClicked
        view_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));
        viewOrders();
    }//GEN-LAST:event_view_icon8MouseClicked

    private void edit_icon8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_icon8MouseClicked

        edit_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit2.png")));
        try {
            //edit orders
            if (orderTable.getRowCount() != 0) {
                for (int i = orderTable.getRowCount() - 1; i >= 0; i--) {
                    orderTable.removeRow(i);
                }
            }
            String name1 = "unknown", id1 = null, quant1 = null;
            String name = orderName.getText();
            int check_id = Integer.parseInt(orderId.getText());
            String client_name = orderClient.getText();
            int quant = Integer.parseInt(orderQuantity.getText());
            float price = Float.parseFloat(orderPrice.getText());
            float total = (float) quant * price;
            float paid = Float.parseFloat(orderPrice.getText());
            int stock_id = 0;
            float rest = total - paid;

            int id = 0;
            int flag = 1;
            int stockQuant = 0;
            orderTotal.setText(String.valueOf(total));
            orderRest.setText(String.valueOf(rest));

            DBcon d = new DBcon();
            String sql = "UPDATE `orders` SET `ID` = '" + check_id + "',`Client_name`='" + client_name + "', `Name` ='" + name + "' , `Quantity` = '" + quant + "', `Price` = '" + price + "', `Total`= '" + total + "', `Paid` = '" + paid + "', `Rest` = '" + rest + "' WHERE ID  = " + check_id + ";";

            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Done   --  تم");
            viewOrders();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }


    }//GEN-LAST:event_edit_icon8MouseClicked

    private void delete_icon8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_icon8MouseClicked

        delete_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete2.png")));
        try {
            // delete orders
            if (orderTable.getRowCount() != 0) {
                for (int i = orderTable.getRowCount() - 1; i >= 0; i--) {
                    orderTable.removeRow(i);
                }
            }
            String name1 = "unknown", id1 = null, quant1 = null;
            int check_id = Integer.parseInt(orderId.getText());

            DBcon d = new DBcon();
            String sql = "DELETE FROM `orders` WHERE `ID` = '" + check_id + "';";

            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Done   --  تم");

            viewOrders();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_delete_icon8MouseClicked

    private void orderTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderTMouseClicked
        // select orders
        DBcon d = new DBcon();

        String id = null, date = null, client_name = "unknown", quant = null, Item = null, price = null, total = null, paid = null, rest = null;
        int n = orderT.getSelectedRow();

        Item = orderTable.getValueAt(n, 1).toString();
        client_name = orderTable.getValueAt(n, 2).toString();
        id = orderTable.getValueAt(n, 0).toString();
        quant = orderTable.getValueAt(n, 3).toString();
        String sql2 = "SELECT * FROM `orders` WHERE ID =" + id + ";";
        try {
            d.rs = d.st.executeQuery(sql2);
            while (d.rs.next()) {
                price = String.valueOf(d.rs.getFloat("Price"));
                total = String.valueOf(d.rs.getFloat("Total"));
                paid = String.valueOf(d.rs.getFloat("Paid"));
                rest = String.valueOf(d.rs.getFloat("Rest"));
                date = String.valueOf(d.rs.getDate("date"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }

        orderName.setText(Item);
        orderId.setText(id);
        orderClient.setText(client_name);
        orderQuantity.setText(quant);
        orderPaid.setText(paid);
        orderPrice.setText(price);
        orderTotal.setText(total);
        orderRest.setText(rest);
        ordersDate3.setText(date);
    }//GEN-LAST:event_orderTMouseClicked

    private void add_icon7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon7MouseClicked

        add_icon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add2.png")));

//return add
        try {
            if (returningTable.getRowCount() != 0) {
                for (int i = returningTable.getRowCount() - 1; i >= 0; i--) {
                    returningTable.removeRow(i);
                }
            }
            String name = returnStockName.getText();
            int check_id;
            if (returnId.getText().isEmpty() == true) {
                check_id = auto_insert("returns", "id");
            } else {
                check_id = Integer.valueOf(returnId.getText());
            }
            String client_name = returnClient.getText();
            int quant = Integer.parseInt(returnQuantity.getText());
            float price = 0;
            float total = 0;
            float paid = Float.parseFloat(returnPaid.getText());
            long stock_id = 0;
            float rest = 0;
            float dept = 0;
            int id = 0;
            int stockQuant = 0;
            LocalDate time = LocalDate.now();
            String sql3;
            String branch = String.valueOf(returnComboBox.getSelectedItem());
            if (returnId.getText().isEmpty()) {
                DBcon d = new DBcon();
                String sql4 = "SELECT * FROM `stock`,branch WHERE stock.branch = branch.id AND stock.Name='" + name + "' AND branch.name = '" + branch + "';";
                d.rs = d.st.executeQuery(sql4);
                while ((d.rs).next()) {
                    stock_id = d.rs.getLong("stock.ID");
                    stockQuant = d.rs.getInt("stock.Quantity");
                    price = d.rs.getFloat("stock.salesPrice");
                }
                returnPrice.setText(String.valueOf(price));
                total = (float) quant * price;
                rest = total - paid;

                returnTotal.setText(String.valueOf(total));
                returnRest.setText(String.valueOf(rest));
                if (deptBox3.isSelected() == false) {
                    rest = 0;
                }
                d.st = d.con.createStatement();

                d.st.executeUpdate("UPDATE `stock`,branch SET stock.`Quantity` = '" + (stockQuant + quant) + "' WHERE branch.id = stock.branch AND stock.ID = '" + stock_id + "' AND branch.name = '" + branch + "';");

                d.rs = d.st.executeQuery("SELECT `clients`.`ID` FROM `clients` WHERE `clients`.`Name`='" + client_name + "';");
                while (d.rs.next()) {
                    id = d.rs.getInt("ID");
                }

                String sql2 = "INSERT INTO `returns` (`id`, `client`, `name`, `quantity`, `price`, `total`, `paid`, `rest`,`date`) VALUES ('" + check_id + "', '" + id + "', '" + stock_id + "', '" + quant + "', '" + price + "', '" + total + "', '" + paid + "', '" + rest + "', '" + time + "');";
                d.st = d.con.createStatement();
                d.st.executeUpdate(sql2);
                JOptionPane.showMessageDialog(null, "Done   --  تم");
            } else {
                JOptionPane.showMessageDialog(null, "Clear Fields And Try Again -- امسح البيانات و حاول مرة اخري");
            }
            viewReturns();
            checkIdPayment.setText(check_id + "");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }


    }//GEN-LAST:event_add_icon7MouseClicked

    private void returnTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_returnTMouseClicked
        // select returns
        DBcon d = new DBcon();

        String id = null, client_name = "unknown", quant = null, stockName = null, price = null, total = null, paid = null, rest = null;
        int n = returnT.getSelectedRow();
        String branch = null;
        stockName = returningTable.getValueAt(n, 1).toString();
        client_name = returningTable.getValueAt(n, 2).toString();
        id = returningTable.getValueAt(n, 0).toString();
        quant = returningTable.getValueAt(n, 3).toString();
        long stock_id = 0;
        String date = null;
        String sql2 = "SELECT * FROM `returns` WHERE id =" + id + ";";
        try {

            d.rs = d.st.executeQuery(sql2);
            while (d.rs.next()) {
                price = String.valueOf(d.rs.getFloat("price"));
                total = String.valueOf(d.rs.getFloat("total"));
                paid = String.valueOf(d.rs.getFloat("paid"));
                rest = String.valueOf(d.rs.getFloat("rest"));
                date = String.valueOf(d.rs.getDate("date"));
                stock_id = d.rs.getLong("name");
            }
            d.rs = d.st.executeQuery("SELECT * FROM stock WHERE ID = " + stock_id + ";");
            while ((d.rs).next()) {
                branch = d.rs.getString("branch");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }

        returnStockName.setText(stockName);
        returnId.setText(id);
        returnClient.setText(client_name);
        returnQuantity.setText(quant);
        returnPaid.setText(paid);
        returnPrice.setText(price);
        returnTotal.setText(total);
        returnRest.setText(rest);
        returnsDate.setText(date);
        returnComboBox.setSelectedItem(branch);

    }//GEN-LAST:event_returnTMouseClicked

    private void view_icon7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon7MouseClicked

        view_icon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));
        viewReturns();
    }//GEN-LAST:event_view_icon7MouseClicked

    private void clear_icon7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon7MouseClicked

        clear_icon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear2.png")));
// clear return
        returnTotal.setText(null);
        returnRest.setText(null);
        returnPaid.setText(null);
        returnPrice.setText(null);
        returnStockName.setText(null);
        returnId.setText(null);
        returnClient.setText(null);
        returnQuantity.setText(null);
        returnStrockId.setText(null);
        deptBox3.setSelected(false);
    }//GEN-LAST:event_clear_icon7MouseClicked

    private void delete_icon3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_icon3MouseClicked
        // sales delete
        delete_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete2.png")));

        try {

            if (salesTable.getRowCount() != 0) {
                for (int i = salesTable.getRowCount() - 1; i >= 0; i--) {
                    salesTable.removeRow(i);
                }
            }
            DBcon d = new DBcon();
            String name = "unknown", client_name = "unknown";
            int check_id = 0, mobile = 0, quant = 0;
            float paid;
            String sql2 = null;
            check_id = Integer.parseInt(salesCheckId.getText());
            name = salesStockName.getText();
            if (name.equals("Multi sales")) {
                String sql3 = "DELETE FROM `sales` WHERE `check_id` ='" + check_id + "';";
                d.st = d.con.createStatement();
                d.st.executeUpdate(sql3);
                d.st = d.con.createStatement();
                d.st.executeUpdate("DELETE FROM `multisales` WHERE `ID` ='" + check_id + "';");
            } else {
                client_name = salesClient.getText();
                quant = Integer.parseInt(salesQuant.getText());
                paid = Float.parseFloat(salespaid.getText());
                long stock_id = 0;
                float dept = 0;
                int id = 0;
                int flag = 1;
                int stockQuant = 0;

                String sql = "SELECT * FROM `clients`";
                String sql4 = "SELECT * FROM `stock` WHERE stock.Name='" + name + "';";
                d.rs = d.st.executeQuery(sql4);
                while ((d.rs).next()) {
                    stock_id = d.rs.getLong("ID");
                    stockQuant = d.rs.getInt("Quantity");
                }
                sql2 = "DELETE FROM `sales` WHERE `check_id` ='" + check_id + "';";
                d.st = d.con.createStatement();
                d.st.executeUpdate(sql2);
                String ss = "UPDATE `stock` SET `Quantity`= '" + (stockQuant + quant) + "' WHERE `ID` = " + stock_id + ";";
                d.st = d.con.createStatement();
                d.st.executeUpdate(ss);
                JOptionPane.showMessageDialog(null, "Done   --  تم");
            }
            viewSales();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_delete_icon3MouseClicked

    private void delete_icon4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_icon4MouseClicked

        delete_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete2.png")));

        //pruchase delete
        try {
            if (purchasesTable.getRowCount() != 0) {
                for (int i = purchasesTable.getRowCount() - 1; i >= 0; i--) {
                    purchasesTable.removeRow(i);
                }
            }
            DBcon d = new DBcon();
            String sql2 = null;
            String name = namePayment.getText();
            int check_id = Integer.parseInt(checkIdPayment.getText());

            sql2 = "DELETE FROM `outgoings` WHERE `check_id` ='" + check_id + "';";
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql2);

            JOptionPane.showMessageDialog(null, "Done   --  تم");
            viewPayment();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }


    }//GEN-LAST:event_delete_icon4MouseClicked

    private void clear_icon4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon4MouseClicked

        clear_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear2.png")));
        //clear purchases
        namePayment.setText(null);
        checkIdPayment.setText(null);
        clientNamePayment.setText(null);
        mobilePayment.setText(null);

        paidPayment.setText(null);
        totalPayment.setText(null);
        restPayment.setText(null);
        notes.setText(null);
        deptBox2.setSelected(false);
        paymentBox1.setSelected(false);


    }//GEN-LAST:event_clear_icon4MouseClicked

    private void add_icon12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon12MouseClicked

        add_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add2.png")));

        //add user
        if (userTable.getRowCount() != 0) {
            for (int i = userTable.getRowCount() - 1; i >= 0; i--) {
                userTable.removeRow(i);
            }
        }
        int[] userPanels = new int[10];
        String name1 = "unknown", id1 = null, type = null;
        String password = this.password.getText();
        String name = userName.getText();
        String branch = String.valueOf(this.branch.getSelectedItem());
        int branchId = 0;
        if (!name.equals("Hash")) {
            String type1 = this.type.getSelectedItem().toString();
            int id = auto_insert("users", "ID");
            userPanels[0] = Boolean.valueOf(user1.isSelected()).compareTo(false);
            userPanels[1] = Boolean.valueOf(user2.isSelected()).compareTo(false);
            userPanels[2] = Boolean.valueOf(user3.isSelected()).compareTo(false);
            userPanels[3] = Boolean.valueOf(user4.isSelected()).compareTo(false);
            userPanels[4] = Boolean.valueOf(user5.isSelected()).compareTo(false);
            userPanels[5] = Boolean.valueOf(user6.isSelected()).compareTo(false);
            userPanels[6] = Boolean.valueOf(user7.isSelected()).compareTo(false);
            userPanels[7] = Boolean.valueOf(user8.isSelected()).compareTo(false);
            userPanels[8] = Boolean.valueOf(user9.isSelected()).compareTo(false);
            userPanels[9] = Boolean.valueOf(user10.isSelected()).compareTo(false);
            try {
                DBcon d = new DBcon();
                d.rs = d.st.executeQuery("select * from branch where name = '" + branch + "';");
                while ((d.rs).next()) {
                    branchId = d.rs.getInt("id");
                }
                String sql = "INSERT INTO `users` (`ID`, `User_name`,`Password`,`logged`, `Type`, `1`, `2`, `3`, `4`, `5`, `6`, `7`, `8`, `9`, `10` , branch) VALUES ('" + id + "', '" + name + "', '" + password + "', '0', '" + type1 + "', '" + userPanels[0] + "', '" + userPanels[1] + "', '" + userPanels[2] + "', '" + userPanels[3] + "', '" + userPanels[4] + "', '" + userPanels[5] + "', '" + userPanels[6] + "', '" + userPanels[7] + "', '" + userPanels[8] + "', '" + userPanels[9] + "' ,'" + branchId + "');";
                d.st = d.con.createStatement();
                d.st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Done   --  تم");
                viewUsers();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري" + e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Try another name   --  جرب اسما اخر ");
        }
    }//GEN-LAST:event_add_icon12MouseClicked

    private void delete_icon12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_icon12MouseClicked

        delete_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete2.png")));

        // delete users
        try {
            if (userTable.getRowCount() != 0) {
                for (int i = userTable.getRowCount() - 1; i >= 0; i--) {
                    userTable.removeRow(i);
                }
            }
            String name1 = "unknown", id1 = null, dept1 = null, email1, mob1, type = null;
            // 
            String password = this.password.getText();
            String name = userName.getText();
            int id = auto_insert("users", "ID");
            DBcon d = new DBcon();
            String sql = "DELETE FROM `users` WHERE `User_name` = '" + userName.getText() + "';";

            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Done   --  تم");
            viewUsers();
            //dm.getDataVector();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }

    }//GEN-LAST:event_delete_icon12MouseClicked

    private void userTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userTMouseClicked
        // select users

        String pass = null, name = null, type = null;
        int n = userT.getSelectedRow();

        name = userT.getValueAt(n, 0).toString();
        pass = userT.getValueAt(n, 1).toString();
        type = userT.getValueAt(n, 2).toString();
        String branch = null;

        DBcon d = new DBcon();
        try {
            d.rs = d.st.executeQuery("select * from users,branch where branch.id = users.branch AND users.User_name = '" + name + "' AND users.Password = '" + pass + "';");
            d.rs.last();
            user1.setSelected(d.rs.getBoolean("1"));
            user2.setSelected(d.rs.getBoolean("2"));
            user3.setSelected(d.rs.getBoolean("3"));
            user4.setSelected(d.rs.getBoolean("4"));
            user5.setSelected(d.rs.getBoolean("5"));
            user6.setSelected(d.rs.getBoolean("6"));
            user7.setSelected(d.rs.getBoolean("7"));
            user8.setSelected(d.rs.getBoolean("8"));
            user9.setSelected(d.rs.getBoolean("9"));
            user10.setSelected(d.rs.getBoolean("10"));
            password.setText(pass);
            userName.setText(name);
            int index = 0;
            if (type.equals("Admin")) {
                index = 2;
            } else {

                index = 1;
            }
            this.type.setSelectedIndex(index);
            this.branch.setSelectedItem(d.rs.getString("branch.name"));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }


    }//GEN-LAST:event_userTMouseClicked

    private void view_icon12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon12MouseClicked
        view_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view2.png")));
        viewUsers();
    }//GEN-LAST:event_view_icon12MouseClicked

    private void edit_icon12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_icon12MouseClicked

        edit_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit2.png")));

        // edit user
        try {

            if (userTable.getRowCount() != 0) {
                for (int i = userTable.getRowCount() - 1; i >= 0; i--) {
                    userTable.removeRow(i);
                }
            }
            String name1 = null, id1 = null, type = null, type1;
            String password = this.password.getText().toString();
            String name = userName.getText().toString();
            type1 = this.type.getSelectedItem().toString();
            DBcon d = new DBcon();
            int[] userPanels = new int[10];
            userPanels[0] = Boolean.valueOf(user1.isSelected()).compareTo(false);
            userPanels[1] = Boolean.valueOf(user2.isSelected()).compareTo(false);
            userPanels[2] = Boolean.valueOf(user3.isSelected()).compareTo(false);
            userPanels[3] = Boolean.valueOf(user4.isSelected()).compareTo(false);
            userPanels[4] = Boolean.valueOf(user5.isSelected()).compareTo(false);
            userPanels[5] = Boolean.valueOf(user6.isSelected()).compareTo(false);
            userPanels[6] = Boolean.valueOf(user7.isSelected()).compareTo(false);
            userPanels[7] = Boolean.valueOf(user8.isSelected()).compareTo(false);
            userPanels[8] = Boolean.valueOf(user9.isSelected()).compareTo(false);
            userPanels[9] = Boolean.valueOf(user10.isSelected()).compareTo(false);
            String sql = "UPDATE `users` SET `Password` = '" + password + "',`Type` = '" + type1 + "', `1` = '" + userPanels[0] + "', `2` = '" + userPanels[1] + "', `3` = '" + userPanels[2] + "', `4` = '" + userPanels[3] + "', `5` = '" + userPanels[4] + "', `6` = '" + userPanels[5] + "', `7` = '" + userPanels[6] + "', `8` = '" + userPanels[7] + "', `9` = '" + userPanels[8] + "', `10` = '" + userPanels[9] + "' WHERE `User_name` = '" + name + "';";
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Done   --  تم");
            viewUsers();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_edit_icon12MouseClicked

    private void passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordActionPerformed

    private void clear_icon12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon12MouseClicked

        clear_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear2.png")));
        password.setText(null);
        userName.setText(null);
        programHours.setText(null);
        type.setSelectedIndex(0);
        //type.setText(null);
        user1.setSelected(false);
        user2.setSelected(false);
        user3.setSelected(false);
        user4.setSelected(false);
        user5.setSelected(false);
        user6.setSelected(false);
        user7.setSelected(false);
        user8.setSelected(false);
        user9.setSelected(false);
        user10.setSelected(false);

    }//GEN-LAST:event_clear_icon12MouseClicked

    private void stock_nameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_stock_nameCaretUpdate
        String name1 = null, quant1 = null, id1 = null, price1 = null;
        if (stockTable.getRowCount() != 0) {
            for (int i = stockTable.getRowCount() - 1; i >= 0; i--) {
                stockTable.removeRow(i);
            }
        }
        String S = stock_name.getText();
        int alarm = 0;
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `stock` WHERE Name LIKE '" + S + "%' AND quantity != -1 and ID != -1 AND ID != 0;";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                name1 = d.rs.getString("Name");
                id1 = String.valueOf(d.rs.getLong("ID"));
                quant1 = String.valueOf(d.rs.getInt("Quantity"));
                price1 = String.valueOf(d.rs.getFloat("Price"));
                String[] rowData = {id1, name1, quant1, price1};
                stockTable.addRow(rowData);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_stock_nameCaretUpdate

    private void clear_icon8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon8MouseClicked

        clear_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear2.png")));
        // clear Orders
        orderId.setText(null);
        orderClient.setText(null);
        orderName.setText(null);
        orderQuantity.setText(null);
        orderPrice.setText(null);
        orderTotal.setText(null);
        orderPaid.setText(null);
        orderRest.setText(null);
        deptBox4.setSelected(false);
    }//GEN-LAST:event_clear_icon8MouseClicked

    private void client_nameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_client_nameCaretUpdate
        // dept filtering
        if (x == 1) {
            if (deptTable.getRowCount() != 0) {
                for (int i = deptTable.getRowCount() - 1; i >= 0; i--) {
                    deptTable.removeRow(i);
                }
            }
            DBcon d = new DBcon();
            DBcon db = new DBcon();
            DBcon dd = new DBcon();
            float dept = 0;
            String name = null;
            int id = 0;
            float rest = 0;
            int temp;
            int clientID = 0;
            float paid;
            String s = client_name.getText();
            String sql;
            String sql2 = "SELECT * FROM `clients` WHERE  clients.Name LIKE '" + s + "%';";

            try {
                dd.rs = dd.st.executeQuery(sql2);
                while ((dd.rs).next()) {
                    clientID = dd.rs.getInt("ID");
                    sql = "SELECT * FROM purchases WHERE rest != 0 AND client_id = " + clientID + ";";
                    try {
                        d.rs = d.st.executeQuery(sql);
                        while (d.rs.next()) {
                            paid = d.rs.getFloat("Paid");
                            rest = d.rs.getFloat("Rest");
                            dept += d.rs.getFloat("Rest");
                            temp = d.rs.getInt("client_id");
                            id = d.rs.getInt("check_id");
                            sql2 = "SELECT clients.Name FROM `clients` WHERE  clients.ID ='" + temp + "';";
                            db.r = db.st.executeQuery(sql2);
                            while (db.r.next()) {
                                name = db.r.getString("Name");
                            }
                            String[] rowData = {String.valueOf(id), name, String.valueOf(rest), String.valueOf(paid)};
                            deptTable.addRow(rowData);

                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Home.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }

                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }

        } else if (x == 2) {
            if (deptTable.getRowCount() != 0) {
                for (int i = deptTable.getRowCount() - 1; i >= 0; i--) {
                    deptTable.removeRow(i);
                }
            }
            DBcon d = new DBcon();
            DBcon db = new DBcon();
            DBcon dd = new DBcon();
            int dept = 0;
            String name = null;
            int id = 0;
            int rest = 0;
            int temp;
            int paid = 0;
            int clientID = 0;
            String s = client_name.getText();
            String sql;//= "SELECT * FROM purchases WHERE rest != 0;";
            String sql2 = "SELECT * FROM `clients` WHERE  clients.Name LIKE '" + s + "%';";

            try {
                dd.rs = dd.st.executeQuery(sql2);
                while ((dd.rs).next()) {
                    clientID = dd.rs.getInt("ID");
                    sql = "SELECT * FROM sales WHERE rest != 0 AND client_id = " + clientID + ";";
                    try {
                        d.rs = d.st.executeQuery(sql);
                        while (d.rs.next()) {
                            paid = d.rs.getInt("Paid");
                            rest = d.rs.getInt("Rest");
                            dept += d.rs.getInt("Rest");
                            temp = d.rs.getInt("client_id");
                            id = d.rs.getInt("check_id");
                            sql2 = "SELECT clients.Name FROM `clients` WHERE  clients.ID ='" + temp + "';";
                            db.r = db.st.executeQuery(sql2);
                            while (db.r.next()) {
                                name = db.r.getString("Name");
                            }
                            String[] rowData = {String.valueOf(id), name, String.valueOf(rest), String.valueOf(paid)};
                            deptTable.addRow(rowData);

                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Home.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
        }
    }//GEN-LAST:event_client_nameCaretUpdate

    private void consNameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_consNameCaretUpdate
        // cons filtering
        String name, id, quant;
        if (consTable.getRowCount() != 0) {
            for (int i = consTable.getRowCount() - 1; i >= 0; i--) {
                consTable.removeRow(i);
            }
        }
        String s = consName.getText();
        DBcon d = new DBcon();

        String sql2 = "SELECT * FROM `stock` WHERE Quantity < 50 AND Name LIKE '" + s + "%';";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                name = d.rs.getString("Name");
                id = String.valueOf(d.rs.getLong("ID"));
                quant = String.valueOf(d.rs.getInt("Quantity"));
                String[] rowData = {name, id, quant};
                consTable.addRow(rowData);
            }
            //dm.getDataVector();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }

    }//GEN-LAST:event_consNameCaretUpdate

    private void orderClientCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_orderClientCaretUpdate
        // filtering order by client
        if (orderTable.getRowCount() != 0) {
            for (int i = orderTable.getRowCount() - 1; i >= 0; i--) {
                orderTable.removeRow(i);
            }
        }
        String name1 = null, id1 = null, quant1 = null, clientName = null, Item = null;

        DBcon d = new DBcon();
        String s = orderClient.getText();
        String sql2 = "SELECT * FROM `orders` WHERE Client_name LIKE '" + s + "%';";
        try {
            d.rs = d.st.executeQuery(sql2);
            while (d.rs.next()) {
                id1 = String.valueOf(d.rs.getInt("ID"));
                quant1 = String.valueOf(d.rs.getInt("Quantity"));
                clientName = d.rs.getString("Client_name");
                Item = d.rs.getString("Name");
                String[] rowData = {id1, Item, clientName, quant1};
                orderTable.addRow(rowData);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_orderClientCaretUpdate

    private void orderNameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_orderNameCaretUpdate
        // filtering order stockName
        if (orderTable.getRowCount() != 0) {
            for (int i = orderTable.getRowCount() - 1; i >= 0; i--) {
                orderTable.removeRow(i);
            }
        }
        String id1 = null, quant1 = null, clientName = null, Item = null, price = null;

        DBcon d = new DBcon();
        String s = orderName.getText();
        String sql2 = "SELECT * FROM `orders` WHERE Name LIKE '" + s + "%';";
        try {
            d.rs = d.st.executeQuery(sql2);
            while (d.rs.next()) {
                id1 = String.valueOf(d.rs.getInt("ID"));
                quant1 = String.valueOf(d.rs.getInt("Quantity"));
                clientName = d.rs.getString("Client_name");
                Item = d.rs.getString("Name");
                String[] rowData = {id1, Item, clientName, quant1};
                orderTable.addRow(rowData);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }

    }//GEN-LAST:event_orderNameCaretUpdate

    private void jTextField64CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextField64CaretUpdate
        // filtering clients
        String name1 = null, quant1 = null, id1 = null;
        if (clientTable.getRowCount() != 0) {
            for (int i = clientTable.getRowCount() - 1; i >= 0; i--) {
                clientTable.removeRow(i);
            }
        }
        DBcon d = new DBcon();
        String s = jTextField64.getText();
        String sql2 = "SELECT * FROM `clients` where Name Like '" + s + "%'";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                name1 = d.rs.getString("Name");
                id1 = String.valueOf(d.rs.getInt("ID"));
                String[] rowData = {id1, name1};
                clientTable.addRow(rowData);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_jTextField64CaretUpdate

    private void userNameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_userNameCaretUpdate
        // user filtering
        if (userTable.getRowCount() != 0) {
            for (int i = userTable.getRowCount() - 1; i >= 0; i--) {
                userTable.removeRow(i);
            }
        }
        String name1, id1 = null, type = null;
        DBcon d = new DBcon();
        String s = userName.getText();
        String sql2 = "SELECT * FROM `users` WHERE User_name LIKE '" + s + "%';";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                name1 = d.rs.getString("User_name");
                id1 = d.rs.getString("Password");
                type = d.rs.getString("Type");

                String[] rowData = {name1, id1, type};
                userTable.addRow(rowData);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_userNameCaretUpdate

    private void add_icon4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon4MouseEntered
        add_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addG.png")));
    }//GEN-LAST:event_add_icon4MouseEntered

    private void view_icon3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon3MouseEntered
        view_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewG.png")));
    }//GEN-LAST:event_view_icon3MouseEntered

    private void view_icon3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon3MouseExited
        view_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewD.png")));
    }//GEN-LAST:event_view_icon3MouseExited

    private void view_icon4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon4MouseEntered
        view_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewG.png")));
    }//GEN-LAST:event_view_icon4MouseEntered

    private void view_icon4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon4MouseExited
        view_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewD.png")));
    }//GEN-LAST:event_view_icon4MouseExited

    private void view_icon6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon6MouseEntered
        view_icon6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewG.png")));
    }//GEN-LAST:event_view_icon6MouseEntered

    private void view_icon6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon6MouseExited
        view_icon6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewD.png")));
    }//GEN-LAST:event_view_icon6MouseExited

    private void view_icon7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon7MouseEntered
        view_icon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewG.png")));
    }//GEN-LAST:event_view_icon7MouseEntered

    private void view_icon7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon7MouseExited
        view_icon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewD.png")));
    }//GEN-LAST:event_view_icon7MouseExited

    private void view_icon8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon8MouseEntered
        view_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewG.png")));
    }//GEN-LAST:event_view_icon8MouseEntered

    private void view_icon8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon8MouseExited
        view_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewD.png")));
    }//GEN-LAST:event_view_icon8MouseExited

    private void view_icon11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon11MouseEntered
        view_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewG.png")));
    }//GEN-LAST:event_view_icon11MouseEntered

    private void view_icon11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon11MouseExited
        view_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewD.png")));
    }//GEN-LAST:event_view_icon11MouseExited

    private void view_icon12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon12MouseEntered
        view_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewG.png")));
    }//GEN-LAST:event_view_icon12MouseEntered

    private void view_icon12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_icon12MouseExited
        view_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/viewD.png")));
    }//GEN-LAST:event_view_icon12MouseExited

    private void add_icon2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon2MouseEntered
        add_icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addG.png")));
    }//GEN-LAST:event_add_icon2MouseEntered

    private void add_icon2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon2MouseExited
        add_icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addD.png")));
    }//GEN-LAST:event_add_icon2MouseExited

    private void add_icon3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon3MouseEntered
        add_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addG.png")));
    }//GEN-LAST:event_add_icon3MouseEntered

    private void add_icon3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon3MouseExited
        add_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addD.png")));
    }//GEN-LAST:event_add_icon3MouseExited

    private void add_icon4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon4MouseExited
        add_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addD.png")));
    }//GEN-LAST:event_add_icon4MouseExited

    private void add_icon7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon7MouseEntered
        add_icon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addG.png")));
    }//GEN-LAST:event_add_icon7MouseEntered

    private void add_icon7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon7MouseExited
        add_icon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addD.png")));
    }//GEN-LAST:event_add_icon7MouseExited

    private void add_icon8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon8MouseEntered
        add_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addG.png")));
    }//GEN-LAST:event_add_icon8MouseEntered

    private void add_icon8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon8MouseExited
        add_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addD.png")));
    }//GEN-LAST:event_add_icon8MouseExited

    private void add_icon11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon11MouseEntered
        add_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addG.png")));
    }//GEN-LAST:event_add_icon11MouseEntered

    private void add_icon11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon11MouseExited
        add_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addD.png")));
    }//GEN-LAST:event_add_icon11MouseExited

    private void add_icon12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon12MouseEntered
        add_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addG.png")));
    }//GEN-LAST:event_add_icon12MouseEntered

    private void add_icon12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_icon12MouseExited
        add_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addD.png")));
    }//GEN-LAST:event_add_icon12MouseExited

    private void delete_icon12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_icon12MouseEntered
        delete_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteG.png")));
    }//GEN-LAST:event_delete_icon12MouseEntered

    private void delete_icon12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_icon12MouseExited
        delete_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteD.png")));
    }//GEN-LAST:event_delete_icon12MouseExited

    private void edit_icon12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_icon12MouseEntered
        edit_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editG.png")));
    }//GEN-LAST:event_edit_icon12MouseEntered

    private void edit_icon12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_icon12MouseExited
        edit_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editD.png")));
    }//GEN-LAST:event_edit_icon12MouseExited

    private void clear_icon12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon12MouseEntered
        clear_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearG.png")));
    }//GEN-LAST:event_clear_icon12MouseEntered

    private void clear_icon12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon12MouseExited
        clear_icon12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearD.png")));
    }//GEN-LAST:event_clear_icon12MouseExited

    private void delete_icon11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_icon11MouseEntered
        delete_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteG.png")));
    }//GEN-LAST:event_delete_icon11MouseEntered

    private void delete_icon11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_icon11MouseExited
        delete_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteD.png")));
    }//GEN-LAST:event_delete_icon11MouseExited

    private void edit_icon11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_icon11MouseEntered
        edit_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editG.png")));
    }//GEN-LAST:event_edit_icon11MouseEntered

    private void edit_icon11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_icon11MouseExited
        edit_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editD.png")));
    }//GEN-LAST:event_edit_icon11MouseExited

    private void clear_icon11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon11MouseEntered
        clear_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearG.png")));
    }//GEN-LAST:event_clear_icon11MouseEntered

    private void clear_icon11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon11MouseExited
        clear_icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearD.png")));
    }//GEN-LAST:event_clear_icon11MouseExited

    private void delete_icon8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_icon8MouseEntered
        delete_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteG.png")));
    }//GEN-LAST:event_delete_icon8MouseEntered

    private void delete_icon8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_icon8MouseExited
        delete_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteD.png")));
    }//GEN-LAST:event_delete_icon8MouseExited

    private void edit_icon8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_icon8MouseEntered
        edit_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editG.png")));
    }//GEN-LAST:event_edit_icon8MouseEntered

    private void edit_icon8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_icon8MouseExited
        edit_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editD.png")));
    }//GEN-LAST:event_edit_icon8MouseExited

    private void clear_icon8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon8MouseEntered
        clear_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearG.png")));
    }//GEN-LAST:event_clear_icon8MouseEntered

    private void clear_icon8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon8MouseExited
        clear_icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearD.png")));
    }//GEN-LAST:event_clear_icon8MouseExited

    private void clear_icon7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon7MouseEntered
        clear_icon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearG.png")));
    }//GEN-LAST:event_clear_icon7MouseEntered

    private void clear_icon7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon7MouseExited
        clear_icon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearD.png")));
    }//GEN-LAST:event_clear_icon7MouseExited

    private void clear_icon6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon6MouseEntered
        clear_icon6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearG.png")));
    }//GEN-LAST:event_clear_icon6MouseEntered

    private void clear_icon6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon6MouseExited
        clear_icon6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearD.png")));
    }//GEN-LAST:event_clear_icon6MouseExited

    private void delete_icon4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_icon4MouseEntered
        delete_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteG.png")));
    }//GEN-LAST:event_delete_icon4MouseEntered

    private void delete_icon4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_icon4MouseExited
        delete_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteD.png")));
    }//GEN-LAST:event_delete_icon4MouseExited

    private void clear_icon4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon4MouseEntered
        clear_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearG.png")));
    }//GEN-LAST:event_clear_icon4MouseEntered

    private void clear_icon4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon4MouseExited
        clear_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearD.png")));
    }//GEN-LAST:event_clear_icon4MouseExited

    private void delete_icon3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_icon3MouseEntered
        delete_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteG.png")));
    }//GEN-LAST:event_delete_icon3MouseEntered

    private void delete_icon3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_icon3MouseExited
        delete_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteD.png")));
    }//GEN-LAST:event_delete_icon3MouseExited

    private void edit_icon3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_icon3MouseEntered
        edit_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editG.png")));
    }//GEN-LAST:event_edit_icon3MouseEntered

    private void edit_icon3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_icon3MouseExited
        edit_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editD.png")));
    }//GEN-LAST:event_edit_icon3MouseExited

    private void clear_icon3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon3MouseEntered
        clear_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearG.png")));
    }//GEN-LAST:event_clear_icon3MouseEntered

    private void clear_icon3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon3MouseExited
        clear_icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearD.png")));
    }//GEN-LAST:event_clear_icon3MouseExited

    private void delete_icon2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_icon2MouseEntered
        delete_icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteG.png")));
    }//GEN-LAST:event_delete_icon2MouseEntered

    private void delete_icon2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_icon2MouseExited
        delete_icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteD.png")));
    }//GEN-LAST:event_delete_icon2MouseExited

    private void edit_icon2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_icon2MouseEntered
        edit_icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editG.png")));
    }//GEN-LAST:event_edit_icon2MouseEntered

    private void edit_icon2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_icon2MouseExited
        edit_icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editD.png")));
    }//GEN-LAST:event_edit_icon2MouseExited

    private void clear_icon2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon2MouseEntered
        clear_icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearG.png")));
    }//GEN-LAST:event_clear_icon2MouseEntered

    private void clear_icon2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon2MouseExited
        clear_icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clearD.png")));
    }//GEN-LAST:event_clear_icon2MouseExited

    private void clear_icon6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clear_icon6MouseClicked

        clear_icon6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear2.png")));
        consQuantity.setText(null);
        consName.setText(null);
        consId.setText(null);
    }//GEN-LAST:event_clear_icon6MouseClicked

    private void returnClient1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_returnClient1CaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_returnClient1CaretUpdate

    private void orderClient1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_orderClient1CaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_orderClient1CaretUpdate

    private void salesDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salesDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_salesDateActionPerformed

    private void returnsDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnsDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_returnsDateActionPerformed

    private void ordersDate3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ordersDate3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ordersDate3ActionPerformed

    private void stocksalePriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stocksalePriceKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_stocksalePriceKeyTyped

    private void add_hoursMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_hoursMouseClicked
        DBcon d = new DBcon();
        String sql = "UPDATE cons SET date = " + programHours.getText() + ";";
        try {
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Done   --  تم");
        } catch (SQLException ex) {
            Logger.getLogger(Home.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_add_hoursMouseClicked

    private void print_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_print_lblMouseClicked
        JOptionPane.showMessageDialog(null, "Printing ... please wait -- سيتم الطباعة الآن");
        if (panalIs == 3) {
            // sales

            ArrayList<PrintableLine> arr = new ArrayList<PrintableLine>(40);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();  //2016/11/16 12:08:43
            String s1, s2;
            float p;
            int q;
            PrintableLine line;
            try {
                line = new PrintableLine("ـــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ");
                arr.add(line);
                line = new PrintableLine(reciept_header1);
                arr.add(line);
                line = new PrintableLine(reciept_header2);
                arr.add(line);
                line = new PrintableLine(reciept_header3);
                arr.add(line);

                line = new PrintableLine("ـــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ");
                arr.add(line);
                s1 = "رقم الشيك               " + user_lbl.getText();

                if (userNameLang.equals("E")) {
                    s1 = user_lbl.getText() + "              رقم الشيك";
                }

                line = new PrintableLine("الوقت ", s1);
                arr.add(line);
                s1 = String.valueOf(dtf.format(now));
                s2 = String.format("%12d", Integer.parseInt(salesCheckId.getText()));
                line = new PrintableLine(s1, s2);
                arr.add(line);
                line = new PrintableLine("ـــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ");
                arr.add(line);
                line = new PrintableLine(" الكمية           السعر          القيمة   ", "اسم الصنف     ");
                arr.add(line);
                line = new PrintableLine("ـــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ");
                arr.add(line);
                q = Integer.parseInt(salesQuant.getText());
                p = Float.parseFloat(salesPrice.getText());

                s1 = formatNumber(String.format("%03.2f", q * p)) + "    " + formatNumber(String.format("%03.2f", p)) + "      " + String.format("%4d", q);
                s2 = salesStockName.getText();
                line = new PrintableLine(s1, s2);
                arr.add(line);

                //line = new PrintableLine("300.25   100.01    0008", "1234كشري   ");
                line = new PrintableLine(" ");
                arr.add(line);
                line = new PrintableLine(" ");
                arr.add(line);

                s1 = String.format("%3.2f", q * p);
                line = new PrintableLine(s1, "التكلفة الكلية   ");
                arr.add(line);
                s1 = String.format("%3.2f", Float.parseFloat(salespaid.getText()));
                line = new PrintableLine(s1, "المدفوع   ");
                arr.add(line);
                s1 = String.format("%3.2f", Float.parseFloat(salesRest.getText()));
                line = new PrintableLine(s1, "المتبقي   ");
                arr.add(line);
                line = new PrintableLine("----------------------------------------------------------");
                arr.add(line);
                line = new PrintableLine(reciept_footer1);
                arr.add(line);
                line = new PrintableLine("----------------------------------------------------------");
                arr.add(line);
                line = new PrintableLine(reciept_footer2);
                arr.add(line);

                PrintReceipt pr = new PrintReceipt(arr);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }

        } else if (panalIs == 4) {
            ArrayList<PrintableLine> arr = new ArrayList<PrintableLine>(40);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();  //2016/11/16 12:08:43
            String s1, s2;
            float p;
            int q;
            PrintableLine line;
            try {
                line = new PrintableLine("ـــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ");
                arr.add(line);
                line = new PrintableLine(reciept_header1);
                arr.add(line);
                line = new PrintableLine(reciept_header2);
                arr.add(line);
                line = new PrintableLine(reciept_header3);
                arr.add(line);

                line = new PrintableLine("ـــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ");
                arr.add(line);
                s1 = "رقم الشيك               " + user_lbl.getText();

                if (userNameLang.equals("E")) {
                    s1 = user_lbl.getText() + "              رقم الشيك";
                }

                line = new PrintableLine("الوقت ", s1);
                arr.add(line);
                s1 = String.valueOf(dtf.format(now));
                s2 = String.format("%12d", Integer.parseInt(salesCheckId.getText()));
                line = new PrintableLine(s1, s2);
                arr.add(line);
                line = new PrintableLine("ـــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ");
                arr.add(line);
                line = new PrintableLine("           القيمة   ", "اسم الصنف     ");
                arr.add(line);
                line = new PrintableLine("ـــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ");
                arr.add(line);

                s1 = formatNumber(String.format("%03.2f", totalPayment.getText()));
                s2 = namePayment.getText();
                line = new PrintableLine(s1, s2);
                arr.add(line);

                //line = new PrintableLine("300.25   100.01    0008", "1234كشري   ");
                line = new PrintableLine(" ");
                arr.add(line);
                line = new PrintableLine(" ");
                arr.add(line);

                s1 = String.format("%3.2f", totalPayment.getText());
                line = new PrintableLine(s1, "التكلفة الكلية   ");
                arr.add(line);
                s1 = String.format("%3.2f", Float.parseFloat(paidPayment.getText()));
                line = new PrintableLine(s1, "المدفوع   ");
                arr.add(line);
                s1 = String.format("%3.2f", Float.parseFloat(restPayment.getText()));
                line = new PrintableLine(s1, "المتبقي   ");
                arr.add(line);
                line = new PrintableLine("----------------------------------------------------------");
                arr.add(line);
                line = new PrintableLine(reciept_footer1);
                arr.add(line);
                line = new PrintableLine("----------------------------------------------------------");
                arr.add(line);
                line = new PrintableLine(reciept_footer2);
                arr.add(line);

                PrintReceipt pr = new PrintReceipt(arr);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
        } else if (panalIs == 8) {
            // sales

            ArrayList<PrintableLine> arr = new ArrayList<PrintableLine>(40);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();  //2016/11/16 12:08:43
            String s1, s2;
            float p;
            int q;
            PrintableLine line;
            try {
                line = new PrintableLine("ـــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ");
                arr.add(line);
                line = new PrintableLine(reciept_header1);
                arr.add(line);
                line = new PrintableLine(reciept_header2);
                arr.add(line);
                line = new PrintableLine(reciept_header3);
                arr.add(line);

                line = new PrintableLine("ـــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ");
                arr.add(line);
                s1 = "رقم الشيك               " + user_lbl.getText();

                if (userNameLang.equals("E")) {
                    s1 = user_lbl.getText() + "              رقم الشيك";
                }

                line = new PrintableLine("الوقت ", s1);
                arr.add(line);
                s1 = String.valueOf(dtf.format(now));
                s2 = String.format("%12d", Integer.parseInt(orderId.getText()));
                line = new PrintableLine(s1, s2);
                arr.add(line);
                line = new PrintableLine("ـــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ");
                arr.add(line);
                line = new PrintableLine(" الكمية           السعر          القيمة   ", "اسم الصنف     ");
                arr.add(line);
                line = new PrintableLine("ـــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ");
                arr.add(line);
                q = Integer.parseInt(orderQuantity.getText());
                p = Float.parseFloat(orderPrice.getText());

                s1 = formatNumber(String.format("%03.2f", q * p)) + "    " + formatNumber(String.format("%03.2f", p)) + "      " + String.format("%4d", q);
                s2 = orderName.getText();
                line = new PrintableLine(s1, s2);
                arr.add(line);

                //line = new PrintableLine("300.25   100.01    0008", "1234كشري   ");
                line = new PrintableLine(" ");
                arr.add(line);
                line = new PrintableLine(" ");
                arr.add(line);

                s1 = String.format("%3.2f", q * p);
                line = new PrintableLine(s1, "التكلفة الكلية   ");
                arr.add(line);
                s1 = String.format("%3.2f", Float.parseFloat(orderPaid.getText()));
                line = new PrintableLine(s1, "المدفوع   ");
                arr.add(line);
                s1 = String.format("%3.2f", Float.parseFloat(orderRest.getText()));
                line = new PrintableLine(s1, "المتبقي   ");
                arr.add(line);
                line = new PrintableLine("----------------------------------------------------------");
                arr.add(line);
                line = new PrintableLine(reciept_footer1);
                arr.add(line);
                line = new PrintableLine("----------------------------------------------------------");
                arr.add(line);
                line = new PrintableLine(reciept_footer2);
                arr.add(line);

                PrintReceipt pr = new PrintReceipt(arr);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }

        }
    }//GEN-LAST:event_print_lblMouseClicked

    private void deptPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deptPaidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deptPaidActionPerformed

    private void salesTMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesTMousePressed
        // select sales 

        String id = null, name1 = "unknown", email = "unknown", mobile = "unknown";
        String Item;
        String check_id;
        String client_name;
        String quant;
        String price = null;
        float total = 0;
        float paid = 0;
        float rest = 0;
        long stock_id = 0;
        String stock_name = null;
        String user = null;
        String branch = null;
        int n = salesT.getSelectedRow();
        Item = salesTable.getValueAt(n, 1).toString();
        check_id = salesTable.getValueAt(n, 0).toString();
        client_name = salesTable.getValueAt(n, 2).toString();
        quant = salesTable.getValueAt(n, 3).toString();
        price = salesTable.getValueAt(n, 4).toString();

        if (Item.equals("multisales")) {
            Idflag = Integer.parseInt(check_id);
            MultiSalesTable m = new MultiSalesTable();
            m.setVisible(true);
            salesCheckId.setText(salesTable.getValueAt(n, 0).toString());
            salesStockName.setText(Item);
            salesQuant.setText(quant);
            salestotal.setText(String.valueOf(price));
            salesStockName.setText("Multi sales");

        } else {
            DBcon d = new DBcon();

            String date = null;
            String sq = "SELECT * FROM sales WHERE check_id = " + check_id + ";";
            try {

                d.rs = d.st.executeQuery(sq);
                while ((d.rs).next()) {
                    user = d.rs.getString("user");
                    // date = d.rs.getString("date");

                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }

            String sql = "SELECT * FROM clients WHERE Name = '" + String.valueOf(salesTable.getValueAt(n, 2)) + "';";
            try {
                d.rs = d.st.executeQuery(sql);
                (d.rs).last();
                mobile = String.valueOf(d.rs.getString("Mobile"));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }

            String sql2 = "SELECT * FROM sales WHERE check_id = " + check_id + ";";
            try {
                d.rs = d.st.executeQuery(sql2);
                (d.rs).last();

                total = d.rs.getFloat("Total");
                paid = d.rs.getFloat("Paid");
                rest = d.rs.getFloat("Rest");
                date = String.valueOf(d.rs.getDate("date"));
                stock_id = d.rs.getLong("stock_id");
                d.rs = d.st.executeQuery("SELECT * FROM stock,branch WHERE stock.branch = branch.id AND stock.ID = " + stock_id + ";");
                while ((d.rs).next()) {
                    branch = d.rs.getString("branch.name");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
            salesCheckId.setText(check_id);
            salesClient.setText(client_name);
            salesMob.setText(mobile);
            salesStockName.setText(Item);
            salesQuant.setText(quant);
            salesPrice.setText(String.valueOf(price));
            salestotal.setText(String.valueOf(total));
            salespaid.setText(String.valueOf(paid));
            salesRest.setText(String.valueOf(rest));
            userSales.setText(user);
            salesDate.setText(date);
            salesStockId.setText(String.valueOf(stock_id));
            salesComboBox.setSelectedItem(branch);
        }
    }//GEN-LAST:event_salesTMousePressed

    private void salespaidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_salespaidFocusGained
        float price = 0;
        int quant = 0;
        price = Float.parseFloat(salesPrice.getText());
        quant = Integer.parseInt(salesQuant.getText());
        salestotal.setText(String.valueOf(price * (float) quant));
    }//GEN-LAST:event_salespaidFocusGained

    private void returnPaidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_returnPaidFocusGained

        returnTotal.setText(String.valueOf(Float.parseFloat(returnPrice.getText()) * (Integer.parseInt(returnQuantity.getText()))));

    }//GEN-LAST:event_returnPaidFocusGained

    private void orderPaidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_orderPaidFocusGained

        orderTotal.setText(String.valueOf(Float.parseFloat(orderPrice.getText()) * (Integer.parseInt(orderQuantity.getText()))));
    }//GEN-LAST:event_orderPaidFocusGained

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void yearLbl4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_yearLbl4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_yearLbl4MouseClicked

    private void yearLbl4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_yearLbl4MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_yearLbl4MousePressed

    private void monthLbl4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_monthLbl4MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_monthLbl4MousePressed

    private void dayLbl4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dayLbl4MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_dayLbl4MousePressed

    private void ok_lbl1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ok_lbl1MouseClicked

        //dateChooserCombo3.getSelectedDate().DAY_OF_MONTH;
        String from = "" + ((dateChooserCombo4.getSelectedDate().getTime().getYear()) + 1900) + "-" + ((dateChooserCombo4.getSelectedDate().getTime().getMonth()) + 1) + "-" + dateChooserCombo4.getSelectedDate().getTime().getDate() + "";
        String to = "" + ((dateChooserCombo3.getSelectedDate().getTime().getYear()) + 1900) + "-" + ((dateChooserCombo3.getSelectedDate().getTime().getMonth()) + 1) + "-" + dateChooserCombo3.getSelectedDate().getTime().getDate() + "";
        //JOptionPane.showMessageDialog(null, from + "      " + to);

        DBcon d = new DBcon();
        DBcon db = new DBcon();
        try {

            String branch = String.valueOf(profitsComboBox1.getSelectedItem());
            float temp = 0;
            float temp1 = 0;
            String stock_id = "";
            String sql = null;
            String sql1;
            String sql2;
            int branchId = 0;
            d.rs = d.st.executeQuery("select * from branch WHERE name = '" + branch + "';");
            while ((d.rs).next()) {
                branchId = d.rs.getInt("id");
            }
            if (branch.equals("all")) {
                sql1 = "SELECT * FROM `returns` WHERE date BETWEEN '" + from + "' AND '" + to + "';";
                sql2 = "SELECT * FROM `sales` WHERE  date BETWEEN '" + from + "' AND '" + to + "';";
            } else {
                sql1 = "SELECT * FROM `returns` , stock WHERE stock.branch = '" + branchId + "' AND  returns.name = stock.ID AND  returns.date BETWEEN '" + from + "' AND '" + to + "' AND stock.ID !=0;";;
                sql2 = "SELECT * FROM `sales` , stock WHERE stock.branch = '" + branchId + "' AND  sales.stock_id = stock.ID AND  sales.date BETWEEN '" + from + "' AND '" + to + "' AND stock.ID !=0;";;
            }
            try {
                db.rs = db.st.executeQuery(sql1);
                while (db.rs.next()) {
                    temp1 += db.rs.getFloat("total");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
            try {
                db.rs = db.st.executeQuery(sql2);
                while (db.rs.next()) {
                    temp += db.rs.getFloat("total");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
            inLabel.setText(String.valueOf(temp - temp1));
        } catch (Exception e) {
        }

        try {

            DBcon dd = new DBcon();
            int quant = 0;
            float temp = 0, paidr = 0, price = 0, rest = 0;
            String branch = String.valueOf(profitsComboBox1.getSelectedItem());
            String sql = null;
            String sql2 = null;
            float temp1 = 0, temp2 = 0;
            int checkId = 0;
            long stockId = 0;
            String stockName = null;
            int branchId = 0;
            db.rs = db.st.executeQuery("select * from branch WHERE name = '" + branch + "';");
            while ((db.rs).next()) {
                branchId = db.rs.getInt("id");
            }
            if (branch.equals("all")) {
                sql = "SELECT * FROM `returns` , stock WHERE returns.name = stock.ID AND returns.date BETWEEN '" + from + "' AND '" + to + "' AND stock.ID !=0;";;
                sql2 = "SELECT * FROM `sales` ,stock  WHERE sales.stock_id = stock.id AND sales.date BETWEEN '" + from + "' AND '" + to + "';";
            } else {
                sql = "SELECT * FROM `returns` , stock WHERE returns.name = stock.ID AND returns.date BETWEEN '" + from + "' AND '" + to + "' AND stock.branch = '" + branchId + "' AND stock.ID !=0;";
                sql2 = "SELECT * FROM `sales` , stock  WHERE sales.stock_id = stock.id AND  sales.date BETWEEN '" + from + "' AND '" + to + "' AND stock.branch = '" + branchId + "' AND stock.ID !=0;";
            }
            try {
                db.rs = db.st.executeQuery(sql);
                while (db.rs.next()) {
                    temp1 += db.rs.getFloat("stock.price") * db.rs.getInt("returns.quantity");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
            try {
                db.rs = db.st.executeQuery(sql2);
                while (db.rs.next()) {
                    quant = db.rs.getInt("sales.quantity");
                    price = db.rs.getFloat("stock.Price");
                    checkId = db.rs.getInt("sales.check_id");
                    stockId = db.rs.getLong("stock.ID");
                    if (stockId == -1) {
                        d.rs = d.st.executeQuery("select * from multisales WHERE ID = '" + checkId + "';");
                        while (d.rs.next()) {
                            stockName = d.rs.getString("Item");
                            dd.rs = dd.st.executeQuery("select * from stock WHERE Name = '" + stockName + "';");
                            dd.rs.last();
                            price = dd.rs.getFloat("stock.Price");
                            quant = d.rs.getInt("multisales.Quant");
                            temp += (price * (float) quant);
                        }
                    } else {
                        temp += (price * (float) quant);
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري" + ex);
            }
            try {
                db.rs = db.st.executeQuery("select * from outgoings where inProfits = 1 AND outgoings.date BETWEEN '" + LocalDate.now().minusDays(1) + "' AND '" + LocalDate.now() + "'");
                while (db.rs.next()) {
                    temp2 += db.rs.getFloat("outgoings.total");
                }
                outLabel.setText(String.valueOf((temp + temp2) - temp1));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        if (Float.parseFloat(inLabel.getText()) < Float.parseFloat(outLabel.getText())) {
            decideImgLbl.setVisible(true);
            decideImgLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thumb_down.png")));
        } else {
            decideImgLbl.setVisible(true);
            decideImgLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thumb_up.png")));
        }
    }//GEN-LAST:event_ok_lbl1MouseClicked

    private void stockComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockComboBoxActionPerformed

//        String name1 = null, quant1 = null, id1 = null, price1 = null;
//        if (stockTable.getRowCount() != 0) {
//            for (int i = stockTable.getRowCount() - 1; i >= 0; i--) {
//                stockTable.removeRow(i);
//            }
//        }
//
//        String branch = String.valueOf(stockComboBox.getSelectedItem());
//        DBcon d = new DBcon();
//        String sql2 = "SELECT * FROM branch,`stock` WHERE branch.id = stock.branch AND branch.name = '" + branch + "';";
//        try {
//            d.rs = d.st.executeQuery(sql2);
//            while ((d.rs).next()) {
//                name1 = d.rs.getString("stock.Name");
//                id1 = String.valueOf(d.rs.getLong("stock.ID"));
//                quant1 = String.valueOf(d.rs.getInt("stock.Quantity"));
//                price1 = String.valueOf(d.rs.getFloat("stock.Price"));
//                String[] rowData = {id1, name1, quant1, price1};
//                stockTable.addRow(rowData);
//            }
//
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
//        }

    }//GEN-LAST:event_stockComboBoxActionPerformed

    private void salesComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salesComboBoxActionPerformed
        String name1 = null, quant1 = null, id1 = null;
        String sql3 = null;
        if (salesTable.getRowCount() != 0) {
            for (int i = salesTable.getRowCount() - 1; i >= 0; i--) {
                salesTable.removeRow(i);
            }
        }
        int stock_id = 0;
        String item = null;
        String price2 = null;
        int stockQuant = 0;
        int rest;
        int total;
        String branch = String.valueOf(salesComboBox.getSelectedItem());
        String sql4 = "SELECT * FROM sales,branch,`stock`,clients WHERE sales.stock_id = stock.ID AND branch.id = stock.branch AND sales.client_id = clients.ID AND branch.name = '" + branch + "';";
        DBcon d = new DBcon();
        try {

            d.rs = d.st.executeQuery(sql4);
            while (d.rs.next()) {
                id1 = String.valueOf(d.rs.getInt("sales.check_id"));
                quant1 = String.valueOf(d.rs.getInt("sales.quantity"));
                name1 = d.rs.getString("clients.Name");
                item = d.rs.getString("stock.Name");
                if (d.rs.getLong("stock.ID") != -1) {
                    price2 = String.valueOf(d.rs.getFloat("sales.Total") / d.rs.getInt("sales.quantity"));
                } else {
                    price2 = String.valueOf(d.rs.getFloat("sales.Total"));
                }
                String[] rowData = {id1, item, name1, quant1, price2};
                salesTable.addRow(rowData);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري" + ex);
        }

    }//GEN-LAST:event_salesComboBoxActionPerformed

    private void consComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consComboBoxActionPerformed
        String name, id, quant;
        int cons;
        if (consTable.getRowCount() != 0) {
            for (int i = consTable.getRowCount() - 1; i >= 0; i--) {
                consTable.removeRow(i);
            }
        }
        String branch = String.valueOf(consComboBox.getSelectedItem());
        DBcon d = new DBcon();

        String sql2 = "SELECT * FROM `stock`,branch WHERE stock.branch = branch.name AND branch.name = '" + branch + "';";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                cons = d.rs.getInt("stock.cons");
                if (cons > d.rs.getInt("stock.Quantity")) {
                    quant = String.valueOf(d.rs.getInt("stock.Quantity"));
                    name = d.rs.getString("stock.Name");
                    id = String.valueOf(d.rs.getLong("stock.ID"));
                    String[] rowData = {name, id, quant};
                    consTable.addRow(rowData);
                }
            }
            //dm.getDataVector();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_consComboBoxActionPerformed

    private void returnComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnComboBoxActionPerformed

        String id1 = null, quant1 = null, sql3, name1 = null, stockName = null;
        int stockId = 0;
        if (returningTable.getRowCount() != 0) {
            for (int i = returningTable.getRowCount() - 1; i >= 0; i--) {
                returningTable.removeRow(i);
            }
        }

        String branch = null;
        branch = String.valueOf(returnComboBox.getSelectedItem());
        DBcon d = new DBcon();
        try {
            d.rs = d.st.executeQuery("SELECT * FROM `stock`,branch,returns,clients WHERE returns.client = clients.id AND returns.name = stock.id AND stock.branch = branch.id AND branch.name = '" + branch + "';");
            while (d.rs.next()) {
                id1 = String.valueOf(d.rs.getInt("returns.id"));
                quant1 = String.valueOf(d.rs.getInt("returns.quantity"));
                name1 = d.rs.getString("clients.Name");
                stockName = d.rs.getString("stock.Name");
            }
            String[] rowData = {id1, stockName, name1, quant1};
            returningTable.addRow(rowData);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }


    }//GEN-LAST:event_returnComboBoxActionPerformed

    private void profitsComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profitsComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_profitsComboBox1ActionPerformed

    private void inventoryComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inventoryComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inventoryComboBoxActionPerformed

    private void returnTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_returnTotalActionPerformed

    private void multiple_sales_lblMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_multiple_sales_lblMouseEntered
        multiple_sales_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/multiple_gray.png")));
    }//GEN-LAST:event_multiple_sales_lblMouseEntered

    private void multiple_sales_lblMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_multiple_sales_lblMouseExited
        multiple_sales_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/multiple_dark.png")));
    }//GEN-LAST:event_multiple_sales_lblMouseExited

    private void multiple_sales_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_multiple_sales_lblMouseClicked
        multiple_sales_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/multiple_white.png")));
        multipleSale r = new multipleSale();
        r.show();
    }//GEN-LAST:event_multiple_sales_lblMouseClicked

    private void add_branchesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_branchesMouseClicked
        DBcon d = new DBcon();
        String s = JOptionPane.showInputDialog("Enter branch Name -- ادخل اسم الفرع");
        String sql = "INSERT INTO `branch` (`name`) VALUES ('" + s + "');";
        try {
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Done   --  تم");
        } catch (Exception e) {
            System.out.println("error " + e);
        }
        combobox();
    }//GEN-LAST:event_add_branchesMouseClicked

    private void stock_idKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stock_idKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            long stock_id = Long.valueOf(this.stock_id.getText());
            String stockName = null, stockPrice = null, salesprice = null, quant = null, alarm = null, branch = null;
            DBcon d = new DBcon();
            String sql1 = "SELECT * FROM `stock`,branch WHERE stock.branch = branch.id AND stock.ID='" + stock_id + "';";
            try {
                d.rs = d.st.executeQuery(sql1);
                d.rs.last();
                stockName = d.rs.getString("stock.Name");
                salesprice = String.valueOf(d.rs.getFloat("salesPrice"));
                stockPrice = String.valueOf(d.rs.getFloat("Price"));
                quant = String.valueOf(d.rs.getInt("quantity"));
                alarm = String.valueOf(d.rs.getInt("cons"));
                branch = d.rs.getString("branch.name");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
            stockComboBox.setSelectedItem(branch);
            stock_name.setText(stockName);
            stock_quan.setText(quant);
            stockpurchasesPrice.setText(stockPrice);
            stocksalePrice.setText(salesprice);
            alarmRange.setText(alarm);

        }
    }//GEN-LAST:event_stock_idKeyPressed

    private void salesCheckIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_salesCheckIdKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int stock_id = 0;
            String check, client = null, mob = null, stockName = null, date = null, total = null, paid = null, rest = null, price = null, quant = null, branch = null;
            DBcon d = new DBcon();
            check = salesCheckId.getText();
            String sql2 = "SELECT * FROM `sales` ,`clients`, stock  WHERE sales.stock_id= stock.ID AND  sales.client_id = `clients`.ID AND sales.check_id = '" + check + "';";
            try {

                d.rs = d.st.executeQuery(sql2);
                d.rs.last();
                stockName = d.rs.getString("stock.Name");
                if (stockName.equals("multisales")) {
                    Idflag = Integer.parseInt(check);
                    MultiSalesTable m = new MultiSalesTable();
                    m.setVisible(true);
                    salesStockName.setText("Multi sales");
                }
                date = String.valueOf(d.rs.getDate("sales.date"));
                client = d.rs.getString("clients.Name");
                mob = String.valueOf(d.rs.getString("clients.mobile"));
                quant = String.valueOf(d.rs.getInt("sales.quantity"));
                total = String.valueOf(d.rs.getFloat("sales.total"));
                paid = String.valueOf(d.rs.getFloat("sales.paid"));
                rest = String.valueOf(d.rs.getFloat("sales.rest"));
                price = String.valueOf((d.rs.getFloat("sales.paid") + d.rs.getFloat("sales.rest")) / d.rs.getInt("quantity"));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري" + ex);
            }
            salesDate.setText(date);
            salesClient.setText(client);
            salesMob.setText(mob);
            salesStockName.setText(stockName);
            salesQuant.setText(quant);
            salesPrice.setText(price);
            salestotal.setText(total);
            salespaid.setText(paid);
            salesRest.setText(rest);
        }
    }//GEN-LAST:event_salesCheckIdKeyPressed

    private void returnIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_returnIdKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String check, client = null, mob = null, stockName = null, date = null, total = null, paid = null, rest = null, price = null, quant = null, branch = null;
            DBcon d = new DBcon();
            check = returnId.getText();
            String sql2 = "SELECT * FROM `returns` ,`clients`, stock , branch WHERE returns.name = stock.ID AND  returns.client = `clients`.ID AND branch.id = stock.branch AND returns.id = '" + check + "';";
            try {
                d.rs = d.st.executeQuery(sql2);
                d.rs.last();
                date = String.valueOf(d.rs.getDate("returns.date"));
                stockName = d.rs.getString("stock.Name");
                client = d.rs.getString("clients.Name");
                branch = String.valueOf(d.rs.getString("branch.name"));
                quant = String.valueOf(d.rs.getInt("returns.quantity"));
                total = String.valueOf(d.rs.getFloat("returns.total"));
                paid = String.valueOf(d.rs.getFloat("returns.paid"));
                rest = String.valueOf(d.rs.getFloat("returns.rest"));
                price = String.valueOf((d.rs.getFloat("returns.paid") + d.rs.getFloat("returns.rest")) / d.rs.getInt("quantity"));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
            returnComboBox.setSelectedItem(branch);
            returnsDate.setText(date);
            returnClient.setText(client);
            //returnStockName.setText(mob);
            returnStockName.setText(stockName);
            returnQuantity.setText(quant);
            returnPrice.setText(price);
            returnTotal.setText(total);
            returnPaid.setText(paid);
            returnRest.setText(rest);
        }
    }//GEN-LAST:event_returnIdKeyPressed

    private void ok_lbl2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ok_lbl2MouseClicked

        String from = "" + ((dateChooserCombo5.getSelectedDate().getTime().getYear()) + 1900) + "-" + ((dateChooserCombo5.getSelectedDate().getTime().getMonth()) + 1) + "-" + dateChooserCombo5.getSelectedDate().getTime().getDate() + "";
        String to = "" + ((dateChooserCombo6.getSelectedDate().getTime().getYear()) + 1900) + "-" + ((dateChooserCombo6.getSelectedDate().getTime().getMonth()) + 1) + "-" + dateChooserCombo6.getSelectedDate().getTime().getDate() + "";
        if (inventoryTable1.getRowCount() != 0) {
            for (int i = inventoryTable1.getRowCount() - 1; i >= 0; i--) {
                inventoryTable1.removeRow(i);
            }
        }
        if (inventoryTable2.getRowCount() != 0) {
            for (int i = inventoryTable2.getRowCount() - 1; i >= 0; i--) {
                inventoryTable2.removeRow(i);
            }
        }
        DBcon db = new DBcon();
        String branch = null;
        String sql = null, sql3 = null, sql2 = null;
        branch = String.valueOf(inventoryComboBox.getSelectedItem());
        int branchId = 0;
        try {
            db.rs = db.st.executeQuery("select * from branch WHERE name = '" + branch + "';");
            while ((db.rs).next()) {
                branchId = db.rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        if (branch.equals("all")) {
            sql = "SELECT * FROM `returns`,stock WHERE returns.name = stock.ID AND returns.date BETWEEN '" + from + "' AND '" + to + "';";
            sql3 = "SELECT * FROM `sales`,stock,clients WHERE sales.stock_id = stock.ID AND sales.client_id = clients.ID AND sales.date BETWEEN '" + from + "' AND '" + to + "' order by sales.date DESC;";
            sql2 = "SELECT * FROM `purchases`,stock ,clients WHERE purchases.stock_id = stock.ID AND purchases.client_id = clients.ID AND purchases.date BETWEEN '" + from + "' AND '" + to + "' order by purchases.date DESC;";
        } else {
            sql = "SELECT * FROM `returns`,stock WHERE returns.name = stock.ID AND stock.branch='" + branchId + "' AND returns.date BETWEEN '" + from + "' AND '" + to + "';";
            sql3 = "SELECT * FROM `sales`,stock ,clients WHERE sales.stock_id = stock.ID AND sales.client_id = clients.ID AND stock.branch='" + branchId + "' AND sales.date BETWEEN '" + from + "' AND '" + to + "' order by sales.date DESC;";
            sql2 = "SELECT * FROM `purchases`,stock ,clients WHERE purchases.stock_id = stock.ID AND purchases.client_id = clients.ID AND stock.branch='" + branchId + "' AND purchases.date BETWEEN '" + from + "' AND '" + to + "' order by purchases.date DESC;";
        }
        String rest, total, date;
        String quant1, name1 = "unknown", Item = null, price = null;
        float temp1 = 0;
        try {
            db.rs = db.st.executeQuery(sql);
            while (db.rs.next()) {
                temp1 += db.rs.getFloat("total");
            }
            return_inventory.setText(String.valueOf(temp1));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
        try {
            float in = 0;
            db.rs = db.st.executeQuery(sql3);

            while (db.rs.next()) {
                quant1 = String.valueOf(db.rs.getInt("sales.quantity"));
                name1 = db.rs.getString("clients.Name");
                Item = db.rs.getString("stock.Name");
                // rest = String.valueOf(db.rs.getFloat("sales.paid"));
                total = String.valueOf(db.rs.getFloat("sales.total"));
                date = db.rs.getString("sales.date");
                // price = String.valueOf(db.rs.getFloat("sales.total") / db.rs.getInt("sales.quantity"));
                String[] rowData = {Item, name1, quant1, total, date};
                inventoryTable1.addRow(rowData);
                in += db.rs.getFloat("total");
            }
            inLabel2.setText(String.valueOf(in));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
        try {
            db.rs = db.st.executeQuery(sql2);
            float in = 0;
            while (db.rs.next()) {
                quant1 = String.valueOf(db.rs.getInt("purchases.quantity"));
                name1 = db.rs.getString("clients.Name");
                Item = db.rs.getString("stock.Name");
                // rest = String.valueOf(db.rs.getFloat("purchases.paid"));
                total = String.valueOf(db.rs.getFloat("purchases.total"));
                date = db.rs.getString("purchases.date");
                // price = String.valueOf(db.rs.getFloat("purchases.total") / db.rs.getInt("purchases.quantity"));
                String[] rowData = {Item, name1, quant1, total, date};
                inventoryTable2.addRow(rowData);
                in += db.rs.getFloat("purchases.total");
            }
            db.rs = db.st.executeQuery("SELECT * FROM `outgoings`,clients WHERE  outgoings.client_id = clients.ID  AND outgoings.inProfits = '" + 1 + "' AND outgoings.date BETWEEN '" + from + "' AND '" + to + "' order by outgoings.date DESC;");
            while (db.rs.next()) {
                name1 = db.rs.getString("clients.Name");
                Item = db.rs.getString("outgoings.stock_name");
                date = db.rs.getString("outgoings.date");
                total = String.valueOf(db.rs.getFloat("outgoings.total"));
                String[] rowData = {Item, name1, "0", total, date};
                inventoryTable2.addRow(rowData);
                in += db.rs.getFloat("outgoings.total");
            }

            inLabel1.setText(String.valueOf(in));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_ok_lbl2MouseClicked

    private void barcode_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barcode_lblMouseClicked

        if (panalIs == 2) {
            try {
                Barcode b = BarcodeFactory.createCode128(stock_id.getText());
                b.setBarHeight(50);
                PrinterJob job = PrinterJob.getPrinterJob();
                job.setPrintable(b);
                if (job.printDialog()) {
                    job.print();
                }
                JOptionPane.showMessageDialog(null, "Printing .. Please wait -- جاري الطباعة");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
        } else {
            File file = new File(barcodeDest + "\\barcode.pdf");
            file.getParentFile().mkdirs();

            int start = 1, end = 2;
            try {
                if (lang_lbl.getText().equals("عربي")) {
                    start = Integer.parseInt(JOptionPane.showInputDialog("Start from : "));
                    end = Integer.parseInt(JOptionPane.showInputDialog("Until : "));

                    JOptionPane.showMessageDialog(null, "please wait the file will open now ..... ");
                    createBarcodePdf(start, end);
                    JOptionPane.showMessageDialog(null, "Done :)");
                    Desktop.getDesktop().open(new File(barcodeDest + "\\barcode.pdf"));

                } else {
                    start = Integer.parseInt(JOptionPane.showInputDialog("البداية : "));
                    end = Integer.parseInt(JOptionPane.showInputDialog("النهاية : "));
                    JOptionPane.showMessageDialog(null, "الرجاء الإنتظار سيتم فتح الملف ..... ");
                    createBarcodePdf(start, end);
                    JOptionPane.showMessageDialog(null, ":) تم بنجاح");
                    Desktop.getDesktop().open(new File(barcodeDest + "\\barcode.pdf"));
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
        }

    }//GEN-LAST:event_barcode_lblMouseClicked

    private void salesStockIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_salesStockIdKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            long stock_id = Long.valueOf(salesStockId.getText());
            String stockName = null, stockPrice = null, salesprice = null, quant = null, alarm = null, branch = null;
            DBcon d = new DBcon();
            String sql1 = "SELECT * FROM `stock`,branch WHERE stock.branch = branch.id AND stock.ID='" + stock_id + "';";
            try {
                d.rs = d.st.executeQuery(sql1);
                d.rs.last();
                stockName = d.rs.getString("stock.Name");
                salesprice = String.valueOf(d.rs.getFloat("salesPrice"));
                branch = d.rs.getString("branch.name");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
            salesComboBox.setSelectedItem(branch);
            salesStockName.setText(stockName);
            salesPrice.setText(salesprice);
        }
    }//GEN-LAST:event_salesStockIdKeyPressed

    private void multiple_return_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_multiple_return_lblMouseClicked
        multiple_return_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/multiple_white.png")));
        multipleReturn r = new multipleReturn();
        r.show();
    }//GEN-LAST:event_multiple_return_lblMouseClicked

    private void multiple_return_lblMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_multiple_return_lblMouseEntered
        multiple_return_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/multiple_gray.png")));
    }//GEN-LAST:event_multiple_return_lblMouseEntered

    private void multiple_return_lblMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_multiple_return_lblMouseExited
        multiple_return_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/multiple_dark.png")));
    }//GEN-LAST:event_multiple_return_lblMouseExited

    private void edit_icon4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_icon4MouseExited
        edit_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editD.png")));
    }//GEN-LAST:event_edit_icon4MouseExited

    private void edit_icon4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_icon4MouseEntered
        edit_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editG.png")));
    }//GEN-LAST:event_edit_icon4MouseEntered

    private void edit_icon4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_icon4MouseClicked

        edit_icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit2.png")));
        try {
            //edit purchases
            if (purchasesTable.getRowCount() != 0) {
                for (int i = purchasesTable.getRowCount() - 1; i >= 0; i--) {
                    purchasesTable.removeRow(i);
                }
            }
            String name = namePayment.getText();
            int check_id = Integer.parseInt(checkIdPayment.getText());
            String client_name = clientNamePayment.getText();
            String mobile = mobilePayment.getText();
            float total = Float.valueOf(totalPayment.getText());
            double paid = Double.parseDouble(paidPayment.getText());
            double rest = total - paid;
            restPayment.setText(String.valueOf(rest));
            DBcon d = new DBcon();
            String sql = "UPDATE outgoings,clients SET  `Paid` = '" + paid + "',  `stock_name` = '" + name + "' , Rest = '" + rest + "' , total = '" + total + "'  WHERE `purchases`.`check_id` = " + check_id + " , Mobile = '" + mobile + "' , clients.Name = " + client_name + ";";
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Done   --  تم");
            viewPayment();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_edit_icon4MouseClicked

    private void paidPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paidPaymentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paidPaymentActionPerformed

    private void paidPaymentFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_paidPaymentFocusGained
        //   float price = Float.parseFloat(pricePurchase.getText());
        // totalPurchase.setText(String.valueOf(price * (Integer.parseInt(quantityPurchase.getText()))));
    }//GEN-LAST:event_paidPaymentFocusGained

    private void restPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restPaymentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_restPaymentActionPerformed

    private void checkIdPaymentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_checkIdPaymentKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            long stock_id = 0;
            String check, user = null, notes = null, client = null, mob = null, stockName = null, date = null, total = null, paid = null, rest = null, price = null, quant = null, branch = null;
            DBcon d = new DBcon();
            check = checkIdPayment.getText();
            String sql2 = "SELECT * FROM `outgoings` ,`clients` WHERE  outgoings.client_id = `clients`.ID AND  outgoings.check_id = '" + check + "';";
            try {
                d.rs = d.st.executeQuery(sql2);
                d.rs.last();
                date = String.valueOf(d.rs.getDate("outgoings.date"));
                stockName = d.rs.getString("outgoings.stock_name");
                client = d.rs.getString("clients.Name");
                user = d.rs.getString("outgoings.user");
                mob = String.valueOf(d.rs.getString("clients.mobile"));
                total = String.valueOf(d.rs.getFloat("outgoings.total"));
                paid = String.valueOf(d.rs.getFloat("outgoings.paid"));
                rest = String.valueOf(d.rs.getFloat("outgoings.rest"));
                notes = d.rs.getString("outgoings.note");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
            paymentDate.setText(date);
            clientNamePayment.setText(client);
            mobilePayment.setText(mob);
            namePayment.setText(stockName);
            totalPayment.setText(total);
            paidPayment.setText(paid);
            restPayment.setText(rest);
            this.notes.setText(notes);
            this.UserPayment.setText(user);
        }
    }//GEN-LAST:event_checkIdPaymentKeyPressed

    private void totalPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalPaymentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalPaymentActionPerformed

    private void paymentBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paymentBox1ActionPerformed

    private void salesCheckIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salesCheckIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_salesCheckIdActionPerformed

    private void returnStrockIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_returnStrockIdKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            long stock_id = Long.valueOf(returnStrockId.getText());
            String stockName = null, stockPrice = null, salesprice = null, quant = null, alarm = null, branch = null;
            DBcon d = new DBcon();
            String sql1 = "SELECT * FROM `stock`,branch WHERE stock.branch = branch.id AND stock.ID='" + stock_id + "';";
            try {
                d.rs = d.st.executeQuery(sql1);
                d.rs.last();
                stockName = d.rs.getString("stock.Name");
                salesprice = String.valueOf(d.rs.getFloat("salesPrice"));
                branch = d.rs.getString("branch.name");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
            }
            returnComboBox.setSelectedItem(branch);
            returnStockName.setText(stockName);
            returnPrice.setText(salesprice);
        }
    }//GEN-LAST:event_returnStrockIdKeyPressed

    private void label13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label13MouseClicked
        JOptionPane.showMessageDialog(null, "متاحة فالإصدار القادم ان شاء الله");
    }//GEN-LAST:event_label13MouseClicked

    private void monthLbl1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_monthLbl1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_monthLbl1MouseClicked

    private void endMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_endMouseClicked
        DBcon d = new DBcon();
        try {
            float total = 0;

            d.rs = d.st.executeQuery("select * from rebellions");
            d.rs.last();
            total = d.rs.getFloat("total");
            this.total.setText(String.valueOf(total));
            d.st = d.con.createStatement();
            d.st.executeUpdate("UPDATE `rebellions` SET `start` = false , total = 0");
            d.st = d.con.createStatement();
            d.st.executeUpdate("TRUNCATE TABLE `rebelliontable`");
        } catch (Exception e) {
        }
        show(jScrollPane5, jScrollPane1, jScrollPane8, jScrollPane9, jScrollPane11, jScrollPane12);
//        //Home.removeColumnSelectionInterval(0, 2);
//        
        String s[] = {"ID", "Item", "Total"};
        homeTable2.setColumnCount(0);
        homeTable2 = createTableCols(Home1, s);
        if (homeTable2.getRowCount() != 0) {
            for (int i = homeTable2.getRowCount() - 1; i >= 0; i--) {
                homeTable2.removeRow(i);
            }
        }
        String name = "unknown", id, quant;

        String sql2 = "SELECT * FROM `rebelliontable`";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                name = d.rs.getString("item");
                id = String.valueOf(d.rs.getInt("id"));
                quant = String.valueOf(d.rs.getFloat("total"));
                String[] rowData = {id, name, quant};
                homeTable2.addRow(rowData);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_endMouseClicked

    private void startMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startMouseClicked
        DBcon d = new DBcon();
        try {

            d.st = d.con.createStatement();
            d.st.executeUpdate("UPDATE `rebellions` SET `start` = 1");

        } catch (Exception e) {
        }
    }//GEN-LAST:event_startMouseClicked

    private void shiftShowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_shiftShowMouseClicked
        show(jScrollPane5, jScrollPane1, jScrollPane8, jScrollPane9, jScrollPane11, jScrollPane12);
//        //Home.removeColumnSelectionInterval(0, 2);
//        
        String s[] = {"ID", "Item", "Total"};
        homeTable2.setColumnCount(0);
        homeTable2 = createTableCols(Home1, s);
        if (homeTable2.getRowCount() != 0) {
            for (int i = homeTable2.getRowCount() - 1; i >= 0; i--) {
                homeTable2.removeRow(i);
            }
        }
        String name = "unknown", id, quant;
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `rebelliontable`";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                name = d.rs.getString("item");
                id = String.valueOf(d.rs.getInt("id"));
                quant = String.valueOf(d.rs.getFloat("total"));
                String[] rowData = {id, name, quant};
                homeTable2.addRow(rowData);
            }
            d.rs = d.st.executeQuery("select * from rebellions");
            d.rs.last();
            float total = d.rs.getFloat("total");
            this.total.setText(String.valueOf(total));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_shiftShowMouseClicked

    private void dayShowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dayShowMouseClicked
        show(jScrollPane5, jScrollPane1, jScrollPane8, jScrollPane9, jScrollPane11, jScrollPane12);

        String s[] = {"ID", "Item", "Total"};
        homeTable2.setColumnCount(0);
        homeTable2 = createTableCols(Home1, s);
        if (homeTable2.getRowCount() != 0) {
            for (int i = homeTable2.getRowCount() - 1; i >= 0; i--) {
                homeTable2.removeRow(i);
            }
        }

        String name = "unknown", id, quant;
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `daytable`";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                name = d.rs.getString("item");
                id = String.valueOf(d.rs.getInt("id"));
                quant = String.valueOf(d.rs.getFloat("total"));
                String[] rowData = {id, name, quant};
                homeTable2.addRow(rowData);
            }
            d.rs = d.st.executeQuery("select * from day");
            d.rs.last();
            float total = d.rs.getFloat("total");
            this.total2.setText(String.valueOf(total));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_dayShowMouseClicked

    private void start1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_start1MouseClicked
        DBcon d = new DBcon();
        try {

            d.st = d.con.createStatement();
            d.st.executeUpdate("UPDATE `day` SET `start` = 1");

        } catch (Exception e) {
        }
    }//GEN-LAST:event_start1MouseClicked

    private void end1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_end1MouseClicked
        DBcon d = new DBcon();
        try {
            float total = 0;

            d.rs = d.st.executeQuery("select * from day");
            d.rs.last();
            total = d.rs.getFloat("total");
            this.total2.setText(String.valueOf(total));
            d.st = d.con.createStatement();
            d.st.executeUpdate("UPDATE `day` SET `start` = false , total = 0");
            d.st = d.con.createStatement();
            d.st.executeUpdate("TRUNCATE TABLE `daytable`");
        } catch (Exception e) {
        }
        show(jScrollPane5, jScrollPane1, jScrollPane8, jScrollPane9, jScrollPane11, jScrollPane12);
//        //Home.removeColumnSelectionInterval(0, 2);
//        
        String s[] = {"ID", "Item", "Total"};
        homeTable2.setColumnCount(0);
        homeTable2 = createTableCols(Home1, s);
        if (homeTable2.getRowCount() != 0) {
            for (int i = homeTable2.getRowCount() - 1; i >= 0; i--) {
                homeTable2.removeRow(i);
            }
        }

        String name = "unknown", id, quant;

        String sql2 = "SELECT * FROM `daytable`";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                name = d.rs.getString("item");
                id = String.valueOf(d.rs.getInt("id"));
                quant = String.valueOf(d.rs.getFloat("total"));
                String[] rowData = {id, name, quant};
                homeTable2.addRow(rowData);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }
    }//GEN-LAST:event_end1MouseClicked

    private void dayShowMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dayShowMouseEntered
        dayShow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye1.png")));
    }//GEN-LAST:event_dayShowMouseEntered

    private void dayShowMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dayShowMouseExited
        dayShow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye2.png")));
    }//GEN-LAST:event_dayShowMouseExited

    private void shiftShowMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_shiftShowMouseEntered
        shiftShow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye1.png")));
    }//GEN-LAST:event_shiftShowMouseEntered

    private void shiftShowMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_shiftShowMouseExited
        shiftShow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye2.png")));
    }//GEN-LAST:event_shiftShowMouseExited

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
               DBcon d = new DBcon();
        try{
         d.rs = d.st.executeQuery("select * from day");
                d.rs.last();
                if (d.rs.getBoolean("start") == false) {
        try {        
            d.st = d.con.createStatement();
            d.st.executeUpdate("UPDATE `day` SET `start` = 1");
        } catch (Exception e) {
        }
                
                }
   
                else{      
        try {
            float total = 0;

            d.rs = d.st.executeQuery("select * from day");
            d.rs.last();
            total = d.rs.getFloat("total");
            this.total2.setText(String.valueOf(total));
            d.st = d.con.createStatement();
            d.st.executeUpdate("UPDATE `day` SET `start` = false , total = 0");
            d.st = d.con.createStatement();
            d.st.executeUpdate("TRUNCATE TABLE `daytable`");
        } catch (Exception e) {
        }
        show(jScrollPane5, jScrollPane1, jScrollPane8, jScrollPane9, jScrollPane11, jScrollPane12);
//        //Home.removeColumnSelectionInterval(0, 2);
//        
        String s[] = {"ID", "Item", "Total"};
        homeTable2.setColumnCount(0);
        homeTable2 = createTableCols(Home1, s);
        if (homeTable2.getRowCount() != 0) {
            for (int i = homeTable2.getRowCount() - 1; i >= 0; i--) {
                homeTable2.removeRow(i);
            }
        }
                }
          } catch (Exception e) {
        }
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
       DBcon d = new DBcon();
        try{
         d.rs = d.st.executeQuery("select * from rebellions");
                d.rs.last();
                if (d.rs.getBoolean("start") == false) {
        try {        
            d.st = d.con.createStatement();
            d.st.executeUpdate("UPDATE `rebellions` SET `start` = 1");
        } catch (Exception e) {
        }
                
                }
   
                else{      
        try {
            float total = 0;

            d.rs = d.st.executeQuery("select * from rebellions");
            d.rs.last();
            total = d.rs.getFloat("total");
            this.total2.setText(String.valueOf(total));
            d.st = d.con.createStatement();
            d.st.executeUpdate("UPDATE `rebellions` SET `start` = false , total = 0");
            d.st = d.con.createStatement();
            d.st.executeUpdate("TRUNCATE TABLE `rebelliontable`");
        } catch (Exception e) {
        }
        show(jScrollPane5, jScrollPane1, jScrollPane8, jScrollPane9, jScrollPane11, jScrollPane12);
//        //Home.removeColumnSelectionInterval(0, 2);
//        
        String s[] = {"ID", "Item", "Total"};
        homeTable2.setColumnCount(0);
        homeTable2 = createTableCols(Home1, s);
        if (homeTable2.getRowCount() != 0) {
            for (int i = homeTable2.getRowCount() - 1; i >= 0; i--) {
                homeTable2.removeRow(i);
            }
        }
                }
          } catch (Exception e) {
        }
    }//GEN-LAST:event_jLabel11MouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ConsPanel;
    private javax.swing.JPanel ConsPanel1;
    private javax.swing.JPanel ConsPanel2;
    private javax.swing.JLabel Date;
    private javax.swing.JTable Home0;
    private javax.swing.JTable Home1;
    private javax.swing.JTable Home2;
    private javax.swing.JTable Home3;
    private javax.swing.JTable Home4;
    private javax.swing.JTable Home5;
    private javax.swing.JTable Home6;
    private javax.swing.JTextField Name;
    private javax.swing.JTextField Total;
    private javax.swing.JTextField UserPayment;
    private javax.swing.JPanel aboutPanel;
    private javax.swing.JPanel aboutPanel1;
    private javax.swing.JLabel add_branches;
    private javax.swing.JLabel add_hours;
    private javax.swing.JLabel add_icon11;
    private javax.swing.JLabel add_icon12;
    private javax.swing.JLabel add_icon2;
    private javax.swing.JLabel add_icon3;
    private javax.swing.JLabel add_icon4;
    private javax.swing.JLabel add_icon7;
    private javax.swing.JLabel add_icon8;
    private javax.swing.JLabel add_lbl11;
    private javax.swing.JLabel add_lbl13;
    private javax.swing.JLabel add_lbl2;
    private javax.swing.JLabel add_lbl3;
    private javax.swing.JLabel add_lbl4;
    private javax.swing.JLabel add_lbl7;
    private javax.swing.JLabel add_lbl8;
    private javax.swing.JTextField alarmRange;
    private javax.swing.JLabel alarm_lbl2;
    private javax.swing.JLabel barcode_lbl;
    private javax.swing.JComboBox<String> branch;
    private javax.swing.JLabel branch_lbl10;
    private javax.swing.JLabel branch_lbl12;
    private javax.swing.JLabel branch_lbl2;
    private javax.swing.JLabel branch_lbl3;
    private javax.swing.JLabel branch_lbl6;
    private javax.swing.JLabel branch_lbl7;
    private javax.swing.JLabel branch_lbl9;
    private javax.swing.JLabel cName_lbl10;
    private javax.swing.JLabel cName_lbl3;
    private javax.swing.JLabel cName_lbl4;
    private javax.swing.JLabel cName_lbl5;
    private javax.swing.JLabel cName_lbl7;
    private javax.swing.JLabel cName_lbl8;
    private javax.swing.JLabel cName_lbl9;
    private javax.swing.JLabel ch_lbl3;
    private javax.swing.JLabel ch_lbl4;
    private javax.swing.JLabel ch_lbl5;
    private javax.swing.JTextField checkIdPayment;
    private javax.swing.JTextField check_id;
    private javax.swing.JLabel check_lbl1;
    private javax.swing.JLabel check_lbl10;
    private javax.swing.JLabel check_lbl2;
    private javax.swing.JLabel check_lbl3;
    private javax.swing.JLabel check_lbl4;
    private javax.swing.JLabel check_lbl5;
    private javax.swing.JLabel check_lbl6;
    private javax.swing.JLabel check_lbl7;
    private javax.swing.JLabel check_lbl8;
    private javax.swing.JLabel check_lbl9;
    private javax.swing.JLabel clear_icon11;
    private javax.swing.JLabel clear_icon12;
    private javax.swing.JLabel clear_icon2;
    private javax.swing.JLabel clear_icon3;
    private javax.swing.JLabel clear_icon4;
    private javax.swing.JLabel clear_icon6;
    private javax.swing.JLabel clear_icon7;
    private javax.swing.JLabel clear_icon8;
    private javax.swing.JTextField clientNamePayment;
    private javax.swing.JTable clientT;
    private javax.swing.JTextField client_name;
    private javax.swing.JPanel clientsPanel;
    private javax.swing.JPanel clientsPanel1;
    private javax.swing.JPanel clientsPanel2;
    private javax.swing.JComboBox<String> consComboBox;
    private javax.swing.JTextField consId;
    private java.util.List<hash_store1.Cons> consList;
    private javax.swing.JTextField consName;
    private javax.swing.JTextField consQuantity;
    private javax.persistence.Query consQuery;
    private javax.swing.JTable consT;
    private javax.swing.JLabel cons_lbl1;
    private datechooser.beans.DateChooserCombo dateChooserCombo3;
    private datechooser.beans.DateChooserCombo dateChooserCombo4;
    private datechooser.beans.DateChooserCombo dateChooserCombo5;
    private datechooser.beans.DateChooserCombo dateChooserCombo6;
    private javax.swing.JLabel date_lbl4;
    private javax.swing.JLabel date_lbl7;
    private javax.swing.JLabel dayLbl1;
    private javax.swing.JLabel dayLbl2;
    private javax.swing.JLabel dayLbl3;
    private javax.swing.JLabel dayLbl4;
    private javax.swing.JLabel dayShow;
    private javax.swing.JLabel decideImgLbl;
    private javax.swing.JLabel del_lbl11;
    private javax.swing.JLabel del_lbl12;
    private javax.swing.JLabel del_lbl2;
    private javax.swing.JLabel del_lbl3;
    private javax.swing.JLabel del_lbl4;
    private javax.swing.JLabel del_lbl8;
    private javax.swing.JLabel delete_icon11;
    private javax.swing.JLabel delete_icon12;
    private javax.swing.JLabel delete_icon2;
    private javax.swing.JLabel delete_icon3;
    private javax.swing.JLabel delete_icon4;
    private javax.swing.JLabel delete_icon8;
    private javax.swing.JLabel dept;
    private javax.swing.JCheckBox deptBox1;
    private javax.swing.JCheckBox deptBox2;
    private javax.swing.JCheckBox deptBox3;
    private javax.swing.JCheckBox deptBox4;
    private javax.swing.JLabel deptL;
    private javax.swing.JLabel deptL1;
    private javax.swing.JLabel deptL2;
    private javax.swing.JTextField deptPaid;
    private javax.swing.JPanel deptPanel;
    private javax.swing.JPanel deptPanel1;
    private javax.swing.JPanel deptPanel2;
    private javax.swing.JTable deptT;
    private javax.swing.JLabel dept_lbl00;
    private javax.swing.JLabel dept_lbl01;
    private javax.swing.JLabel dept_lbl11;
    private javax.swing.JLabel edit_icon11;
    private javax.swing.JLabel edit_icon12;
    private javax.swing.JLabel edit_icon2;
    private javax.swing.JLabel edit_icon3;
    private javax.swing.JLabel edit_icon4;
    private javax.swing.JLabel edit_icon8;
    private javax.swing.JLabel edit_lbl11;
    private javax.swing.JLabel edit_lbl12;
    private javax.swing.JLabel edit_lbl2;
    private javax.swing.JLabel edit_lbl3;
    private javax.swing.JLabel edit_lbl4;
    private javax.swing.JLabel edit_lbl8;
    private javax.swing.JLabel end;
    private javax.swing.JLabel end1;
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JLabel eyeLabel1;
    private javax.swing.JLabel eyeLabel2;
    private javax.swing.JLabel eyeLabel3;
    private javax.swing.JLabel eyeLabel4;
    private javax.swing.JLabel eyeLabel5;
    private javax.swing.JLabel eyeLabel6;
    private javax.swing.JLabel from_lbl1;
    private javax.swing.JLabel from_lbl2;
    private javax.swing.JPanel fullPanel;
    private javax.swing.JPanel helpPanel;
    private javax.swing.JPanel helpPanel1;
    private javax.swing.JPanel helpPanel2;
    private javax.swing.JPanel homePanel;
    private javax.swing.JPanel homePanel1;
    private javax.swing.JPanel homePanel2;
    private javax.swing.JLabel hours_lbl;
    private javax.swing.JLabel id2_lbl9;
    private javax.swing.JLabel id_lbl11;
    private javax.swing.JLabel id_lbl2;
    private javax.swing.JLabel id_lbl6;
    private javax.swing.JLabel id_lbl7;
    private javax.swing.JLabel id_lbl8;
    private javax.swing.JLabel inLabel;
    private javax.swing.JLabel inLabel1;
    private javax.swing.JLabel inLabel2;
    private javax.swing.JLabel inLabel4;
    private javax.swing.JLabel inLabel5;
    private javax.swing.JLabel inLabel6;
    private javax.swing.JLabel inLabel7;
    private javax.swing.JLabel inLabel8;
    private javax.swing.JLabel inLabel9;
    private javax.swing.JComboBox<String> inventoryComboBox;
    private javax.swing.JPanel inventoryPanel;
    private javax.swing.JPanel inventoryPanel1;
    private javax.swing.JPanel inventoryPanel2;
    private javax.swing.JTable inventoryT;
    private javax.swing.JTable inventoryT1;
    private javax.swing.JPanel itemPanel10;
    private javax.swing.JPanel itemPanel11;
    private javax.swing.JPanel itemPanel2;
    private javax.swing.JPanel itemPanel3;
    private javax.swing.JPanel itemPanel4;
    private javax.swing.JPanel itemPanel5;
    private javax.swing.JPanel itemPanel6;
    private javax.swing.JPanel itemPanel7;
    private javax.swing.JPanel itemPanel8;
    private javax.swing.JLabel item_lbl3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField61;
    private javax.swing.JTextField jTextField64;
    private javax.swing.JTextField jTextField70;
    private javax.swing.JTextField jTextField83;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel label10;
    private javax.swing.JLabel label11;
    private javax.swing.JLabel label12;
    private javax.swing.JLabel label13;
    private javax.swing.JLabel label14;
    private javax.swing.JLabel label2;
    private javax.swing.JLabel label3;
    private javax.swing.JLabel label4;
    private javax.swing.JLabel label5;
    private javax.swing.JLabel label6;
    private javax.swing.JLabel label7;
    private javax.swing.JLabel label8;
    private javax.swing.JLabel label9;
    public static javax.swing.JLabel lang_lbl;
    private javax.swing.JLabel mob_lbl11;
    private javax.swing.JLabel mob_lbl3;
    private javax.swing.JLabel mob_lbl4;
    private javax.swing.JLabel mob_lbl5;
    private javax.swing.JLabel mob_lbl6;
    private javax.swing.JTextField mobilePayment;
    private javax.swing.JLabel monthLbl1;
    private javax.swing.JLabel monthLbl2;
    private javax.swing.JLabel monthLbl3;
    private javax.swing.JLabel monthLbl4;
    private javax.swing.JLabel multiple_return_lbl;
    private javax.swing.JLabel multiple_sales_lbl;
    private javax.swing.JLabel myDeptE;
    private javax.swing.JLabel myDeptL;
    private javax.swing.JTextField namePayment;
    private javax.swing.JLabel name_lbl11;
    private javax.swing.JLabel name_lbl2;
    private javax.swing.JLabel name_lbl3;
    private javax.swing.JLabel name_lbl4;
    private javax.swing.JLabel name_lbl5;
    private javax.swing.JLabel name_lbl6;
    private javax.swing.JLabel name_lbl7;
    private javax.swing.JLabel name_lbl8;
    private javax.swing.JTextArea notes;
    private javax.swing.JLabel notes_lbl;
    private javax.swing.JLabel ok_lbl1;
    private javax.swing.JLabel ok_lbl2;
    private javax.swing.JTextField orderClient;
    private javax.swing.JTextField orderClient1;
    private javax.swing.JTextField orderId;
    private javax.swing.JTextField orderName;
    private javax.swing.JTextField orderPaid;
    private javax.swing.JPanel orderPanel;
    private javax.swing.JPanel orderPanel1;
    private javax.swing.JPanel orderPanel2;
    private javax.swing.JTextField orderPrice;
    private javax.swing.JTextField orderQuantity;
    private javax.swing.JTextField orderRest;
    private javax.swing.JTable orderT;
    private javax.swing.JTextField orderTotal;
    private javax.swing.JLabel order_dept_lbl;
    private javax.swing.JTextField ordersDate3;
    private java.util.List<hash_store1.Orders> ordersList;
    private javax.persistence.Query ordersQuery;
    private javax.swing.JLabel orders_lbl1;
    private javax.swing.JLabel outLabel;
    private javax.swing.JLabel outLabel1;
    private javax.swing.JLabel outLabel9;
    private javax.swing.JTextField paidPayment;
    private javax.swing.JLabel paid_lbl3;
    private javax.swing.JLabel paid_lbl4;
    private javax.swing.JLabel paid_lbl7;
    private javax.swing.JLabel paid_lbl8;
    private javax.swing.JPanel pane1;
    private javax.swing.JPanel pane10;
    private javax.swing.JPanel pane11;
    private javax.swing.JPanel pane12;
    private javax.swing.JPanel pane13;
    private javax.swing.JPanel pane14;
    private javax.swing.JPanel pane2;
    private javax.swing.JPanel pane3;
    private javax.swing.JPanel pane4;
    private javax.swing.JPanel pane5;
    private javax.swing.JPanel pane6;
    private javax.swing.JPanel pane7;
    private javax.swing.JPanel pane8;
    private javax.swing.JPanel pane9;
    private javax.swing.JLabel pass_lbl12;
    private javax.swing.JTextField password;
    private javax.swing.JCheckBox paymentBox1;
    private javax.swing.JTextField paymentDate;
    private javax.swing.JPanel paymentPanel;
    private javax.swing.JPanel paymentPanel1;
    private javax.swing.JPanel paymentPanel2;
    private javax.swing.JTable paymentT;
    private javax.swing.JLabel payment_lbl;
    private javax.swing.JLabel pdf_lbl;
    private javax.swing.JLabel price_lbl2;
    private javax.swing.JLabel price_lbl3;
    private javax.swing.JLabel price_lbl6;
    private javax.swing.JLabel price_lbl7;
    private javax.swing.JLabel price_lbl8;
    private javax.swing.JLabel print_lbl;
    private javax.swing.JPanel profitPanel;
    private javax.swing.JPanel profitPanel1;
    private javax.swing.JPanel profitPanel2;
    private javax.swing.JPanel profitPanel3;
    private javax.swing.JComboBox<String> profitsComboBox1;
    private javax.swing.JTextField programHours;
    private javax.swing.JLabel purchase_lbl1;
    private java.util.List<hash_store1.Purchases> purchasesList;
    private javax.persistence.Query purchasesQuery;
    private javax.swing.JLabel quan_lbl2;
    private javax.swing.JLabel quan_lbl3;
    private javax.swing.JLabel quan_lbl6;
    private javax.swing.JLabel quan_lbl7;
    private javax.swing.JLabel quan_lbl8;
    private javax.swing.JTextField restPayment;
    private javax.swing.JLabel rest_lbl10;
    private javax.swing.JLabel rest_lbl3;
    private javax.swing.JLabel rest_lbl4;
    private javax.swing.JLabel rest_lbl5;
    private javax.swing.JLabel rest_lbl7;
    private javax.swing.JLabel rest_lbl8;
    private javax.swing.JTextField returnClient;
    private javax.swing.JTextField returnClient1;
    private javax.swing.JComboBox<String> returnComboBox;
    private javax.swing.JTextField returnId;
    private javax.swing.JTextField returnPaid;
    private javax.swing.JPanel returnPanel;
    private javax.swing.JPanel returnPanel1;
    private javax.swing.JPanel returnPanel2;
    private javax.swing.JTextField returnPrice;
    private javax.swing.JTextField returnQuantity;
    private javax.swing.JTextField returnRest;
    private javax.swing.JTextField returnStockName;
    private javax.swing.JTextField returnStrockId;
    private javax.swing.JTable returnT;
    private javax.swing.JTextField returnTotal;
    private javax.swing.JLabel return_dept_lbl;
    private javax.swing.JLabel return_inventory;
    private javax.swing.JLabel return_lbl;
    private javax.swing.JTextField returnsDate;
    private java.util.List<hash_store1.Returns> returnsList;
    private javax.persistence.Query returnsQuery;
    private javax.swing.JLabel returns_lbl1;
    private javax.swing.JTextField salesCheckId;
    private javax.swing.JTextField salesClient;
    private javax.swing.JComboBox<String> salesComboBox;
    private javax.swing.JTextField salesDate;
    private java.util.List<hash_store1.Sales> salesList;
    private java.util.List<hash_store1.Sales> salesList1;
    private javax.swing.JTextField salesMob;
    private javax.swing.JPanel salesPanel;
    private javax.swing.JPanel salesPanel1;
    private javax.swing.JPanel salesPanel2;
    private javax.swing.JTextField salesPrice;
    private javax.swing.JTextField salesQuant;
    private javax.persistence.Query salesQuery;
    private javax.persistence.Query salesQuery1;
    private javax.swing.JTextField salesRest;
    private javax.swing.JTextField salesStockId;
    private javax.swing.JTextField salesStockName;
    public javax.swing.JTable salesT;
    private javax.swing.JLabel sales_lbl1;
    private javax.swing.JTextField salespaid;
    private javax.swing.JTextField salestotal;
    private javax.swing.JLabel search_lbl11;
    private javax.swing.JLabel search_lbl12;
    private javax.swing.JLabel search_lbl2;
    private javax.swing.JLabel search_lbl3;
    private javax.swing.JLabel search_lbl4;
    private javax.swing.JLabel search_lbl6;
    private javax.swing.JLabel search_lbl7;
    private javax.swing.JLabel search_lbl8;
    private javax.swing.JLabel shiftShow;
    private javax.swing.JPanel sideBorderPanel;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JLabel start;
    private javax.swing.JLabel start1;
    private javax.swing.JComboBox<String> stockComboBox;
    private java.util.List<hash_store1.Stock> stockList;
    private java.util.List<hash_store1.Stock> stockList1;
    private java.util.List<hash_store1.Stock> stockList10;
    private java.util.List<hash_store1.Stock> stockList2;
    private java.util.List<hash_store1.Stock> stockList3;
    private java.util.List<hash_store1.Stock> stockList4;
    private java.util.List<hash_store1.Stock> stockList5;
    private java.util.List<hash_store1.Stock> stockList6;
    private java.util.List<hash_store1.Stock> stockList7;
    private java.util.List<hash_store1.Stock> stockList8;
    private java.util.List<hash_store1.Stock> stockList9;
    private javax.swing.JPanel stockPanel;
    private javax.swing.JPanel stockPanel1;
    private javax.swing.JPanel stockPanel2;
    private javax.persistence.Query stockQuery;
    private javax.persistence.Query stockQuery1;
    private javax.persistence.Query stockQuery10;
    private javax.persistence.Query stockQuery2;
    private javax.persistence.Query stockQuery3;
    private javax.persistence.Query stockQuery4;
    private javax.persistence.Query stockQuery5;
    private javax.persistence.Query stockQuery6;
    private javax.persistence.Query stockQuery7;
    private javax.persistence.Query stockQuery8;
    private javax.persistence.Query stockQuery9;
    private javax.swing.JTextField stock_id;
    private javax.swing.JLabel stock_lbl1;
    private javax.swing.JTextField stock_name;
    private javax.swing.JTextField stock_quan;
    private javax.swing.JTextField stockpurchasesPrice;
    private javax.swing.JTextField stocksalePrice;
    private javax.swing.JPanel titlePanel;
    private javax.swing.JLabel to_lbl1;
    private javax.swing.JLabel to_lbl2;
    private javax.swing.JLabel total;
    private javax.swing.JLabel total2;
    private javax.swing.JTextField totalPayment;
    private javax.swing.JLabel total_lbl3;
    private javax.swing.JLabel total_lbl5;
    private javax.swing.JLabel total_lbl6;
    private javax.swing.JLabel total_lbl7;
    private javax.swing.JLabel total_lbl8;
    private javax.swing.JLabel total_lbl9;
    private javax.swing.JComboBox<String> type;
    private javax.swing.JLabel type_lbl1;
    private javax.swing.JLabel type_lbl12;
    private javax.swing.JCheckBox user1;
    private javax.swing.JCheckBox user10;
    private javax.swing.JCheckBox user2;
    private javax.swing.JCheckBox user3;
    private javax.swing.JCheckBox user4;
    private javax.swing.JCheckBox user5;
    private javax.swing.JCheckBox user6;
    private javax.swing.JCheckBox user7;
    private javax.swing.JCheckBox user8;
    private javax.swing.JCheckBox user9;
    private javax.swing.JTextField userName;
    private javax.swing.JTextField userSales;
    private javax.swing.JTable userT;
    public static javax.swing.JLabel user_lbl;
    private javax.swing.JLabel user_lbl12;
    private javax.swing.JPanel usersPanel;
    private javax.swing.JPanel usersPanel4;
    private javax.swing.JPanel usersPanel5;
    private javax.swing.JLabel videoPlay1;
    private javax.swing.JLabel view_icon11;
    private javax.swing.JLabel view_icon12;
    private javax.swing.JLabel view_icon2;
    private javax.swing.JLabel view_icon3;
    private javax.swing.JLabel view_icon4;
    private javax.swing.JLabel view_icon6;
    private javax.swing.JLabel view_icon7;
    private javax.swing.JLabel view_icon8;
    private javax.swing.JLabel view_lbl11;
    private javax.swing.JLabel view_lbl12;
    private javax.swing.JLabel view_lbl2;
    private javax.swing.JLabel view_lbl3;
    private javax.swing.JLabel view_lbl4;
    private javax.swing.JLabel view_lbl6;
    private javax.swing.JLabel view_lbl7;
    private javax.swing.JLabel view_lbl8;
    private javax.swing.JLabel yearLbl1;
    private javax.swing.JLabel yearLbl2;
    private javax.swing.JLabel yearLbl3;
    private javax.swing.JLabel yearLbl4;
    // End of variables declaration//GEN-END:variables
}
