import java.util.ArrayList;
import java.util.List;
//*BondedStack - ADT แทนระบบจัดเก็บหนังสือในห้องสมุดที่มีการจำกัดจำนวนหนังสือที่จุได้ */
/*จัดเก็บหนังสือในห้องสมุดเป็น String ในช่วง 1 ถึง 500 */
/*ทำงานตามหลักของ Stack (LIFO - Last In, First Out) */
public class BondedStack {
   public static final int MIN_SIZE = 1; //จำนวนหนังสือที่น้อยที่สุดที่สามารถจัดเก็บได้
   public static final int MAX_SIZE = 500; //จำนวนหนังสือที่มากที่สุดที่สามารถจัดเก็บได้
   //representation//
    private final List<String> booknum; //ใช้ ArrayList ในการจัดเก็บหนังสือ
    private final int capacity; //จำนวนหนังสือที่สามารถจัดเก็บได้สูงสุด

    //TODO1 : Abstraction Function:
    //AF(Book number of books in the stack, capacity of the stack) = จำนวนหนังสือที่มีจำนวนสูงสุดที่สามารถจัดเก็บได้ capacity เล่ม
    //เรียงหนังสือที่ถูกจัดเก็บในห้องสมุดจากเล่มแรก ((booknum.get(0))
    //เรียงหนังสือที่ถูกจัดเก็บในห้องสมุดจากเล่มสุดท้าย (booknum.get(booknum.size()-1))
    
    //TODO2 : Representation Invariant:
    // -booknum != null หนังสือที่จัดเก็บในห้องสมุดต้องไม่เป็น null และไม่เป็น String ว่าง
    // -capacity >= MIN_SIZE && capacity <= MAX_SIZE  จำนวนหนังสือที่จัดเก็บในห้องสมุดต้องอยู่ในช่วง MIN_SIZE ถึง MAX_SIZE (1 ถึง 500)
    // -จำนวนหนังสือที่จัดเก็บในห้องสมุดต้องไม่เกิน capacity ของห้องสมุด
    // -จำนวนหนังสือ เมื่อแปลงเป็นตัวเลขแล้วต้องอยู่ในช่วง 1 ถึง 500 (MIN_SIZE ถึง MAX_SIZE)

    //TODO3 : เขียน safety from rep exposure ตรงนี้
    // safety from rep exposure:
    // - ตัวแปร booknum เป็น private และไม่สามารถเข้าถึงได้จากภายนอกคลาส
    // - ตัวแปร capacity เป็น final และไม่สามารถเปลี่ยนแปลงได้จากภายนอกคลาส
    // - คืนค่าของข้อมูลด้วยการ copy ของข้อมูลแทนการคืนค่าตัวแปรโดยตรง 
    // - รับข้อมูลจากการตรวจสอบ และ copy ข้อมูลเพื่อรับข้อมูลใหม่เสมอ

    //TODO4 : เขียน checkRep() 
    //แปลง RI ทุกตัวเป็น assert หนึ่งบรรทัด พร้อมข้อความอธิบาย
    private void checkRep(){
        assert booknum != null : "booknum must not be null"; //ตรวจสอบว่า booknum ไม่เป็น null
        assert capacity >=MIN_SIZE : "capacity must be greater than or equal to " + MIN_SIZE; //ตรวจสอบว่าความจุของห้องสมุดต้องมากกว่าหรือเท่ากับ MIN_SIZE
        assert capacity <= MAX_SIZE : "capacity must be less than or equal to " + MAX_SIZE; //ตรวจสอบว่าความจุของห้องสมุดต้องน้อยกว่าหรือเท่ากับ MAX_SIZE
        assert booknum.size() <= capacity : "booknum size must not exceed capacity"; //ตรวจสอบว่าจำนวนหนังสือที่จัดเก็บในห้องสมุดต้องไม่เกินความจุของห้องสมุด
        
        for (String book : booknum) {// ตรวจสอบหนังสือทีละเล่ม (เช็คหนังสือทุกเล่ม)
            assert book != null && !book.isEmpty() : "book must not be null or empty"; //ตรวจสอบว่าหนังสือไม่เป็น null หรือ String ว่าง
            int bookNumber = Integer.parseInt(book); //แปลงหนังสือเป็นตัวเลข
            assert bookNumber >= MIN_SIZE && bookNumber <= MAX_SIZE : "book number must be between " + MIN_SIZE + " and " + MAX_SIZE; //ตรวจสอบว่าหนังสืออยู่ในช่วง 1 ถึง 500
           
            try { //ลองแปลงหนังสือเป็นตัวเลข จาก String และตรวจสอบว่าหนังสืออยู่ในช่วง 1 ถึง 500
                bookNumber = Integer.parseInt(book);
                assert bookNumber >= MIN_SIZE && bookNumber <= MAX_SIZE : "book number must be between " + MIN_SIZE + " and " + MAX_SIZE;
    }           catch (NumberFormatException e) { //ถ้าแปลงหนังสือเป็นตัวเลขไม่ได้ ให้แสดงข้อความผิดพลาด
                assert false : "book must be a valid integer"; //ตรวจสอบว่าหนังสือเป็นตัวเลขที่ถูกต้อง
            }
        }
    }
// Creaters//
/**
 * สร้างห้องสมุดที่มีความจุสูงสุดเท่ากับ capacity
 * @param capacity ความจุสูงสุดของห้องสมุด (ต้องมากกว่า 0)
 * @throws IllegalArgumentException ถ้า capacity น้อยกว่าหรือเท่ากับ 0
 */
public BondedStack(int capacity) {
    if (capacity <= 0) {
        throw new IllegalArgumentException("Capacity must be greater than 0");
    }
    if (capacity > MAX_SIZE) {
        throw new IllegalArgumentException("Capacity must not exceed " + MAX_SIZE);
    }
    this.capacity = capacity;
    this.booknum = new ArrayList<>();
    checkRep();
}
//Helper Methods//
private void validateBook(String book) {
    if (book == null || book.isEmpty()) {
        throw new IllegalArgumentException("Book must not be null or empty");
    }

    try {
        int bookNumber = Integer.parseInt(book);

        if (bookNumber < MIN_SIZE || bookNumber > MAX_SIZE) {
            throw new IllegalArgumentException(
                "Book number must be between " + MIN_SIZE + " and " + MAX_SIZE);
        }
    } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Book must be a valid integer");
    }

    if (booknum.contains(book)) {
        throw new IllegalArgumentException("Book is already in the stack");
    }
}
//Mutators//
//*ก่อนเพิ่มหนังสือต้องตรวจสอบว่าห้องสมุดเต็มหรือไม่ ถ้าเต็มจะ throw IllegalStateException
// *ใส่หนังสือที่เข้ามาใหม่ในห้องสมุด
//*@param book หนังสือที่ต้องการเพิ่มในห้องสมุด
//*@throws IllegalStateException ถ้าห้องสมุดเต็ม
//*@throws IllegalArgumentException ถ้าหนังสือไม่ถูกต้อง
public void push(String book) {//เพิ่มหนังสือในห้องสมุด
    validateBook(book); //ตรวจสอบความถูกต้องของหนังสือ
    if (booknum.size() >= capacity) { //ตรวจสอบว่าห้องสมุดเต็มหรือไม่
        throw new IllegalStateException("Stack is full");
    }
    booknum.add(book); //เพิ่มหนังสือในห้องสมุด
    checkRep(); //ตรวจสอบ Representation Invariant
}
//*ดึงหนังสือที่อยู่บนสุดของห้องสมุดออกมา
//*@return หนังสือที่อยู่บนสุดของห้องสมุด
//*@throws IllegalStateException ถ้าห้องสมุดว่าง
public String pop() {//ดึงหนังสือที่อยู่บนสุดของห้องสมุดออกมา
    if (booknum.isEmpty()) { //ตรวจสอบว่าห้องสมุดว่างหรือไม่
        throw new IllegalStateException("Stack is empty");
    }
    String book = booknum.remove(booknum.size() - 1); //ดึงหนังสือที่อยู่บนสุดของห้องสมุดออกมา
    checkRep(); //ตรวจสอบ Representation Invariant
    return book; //คืนค่าหนังสือที่อยู่บนสุดของห้องสมุด
}
//Observers//
//เช็คว่าห้องสมุดว่างหรือไม่ และเช็คจำนวนพื้นที่ว่างในห้องสมุด
/**
 * ดูหนังสือที่อยู่บนสุดของห้องสมุดโดยไม่ดึงออกมา
 */
public String peek() {//ดูหนังสือที่อยู่บนสุดของห้องสมุดโดยไม่ดึงออกมา
    if (booknum.isEmpty()) { //ตรวจสอบว่าห้องสมุดว่างหรือไม่
        throw new IllegalStateException("Stack is empty");
    }
    return booknum.get(booknum.size() - 1); //คืนค่าหนังสือที่อยู่บนสุดของห้องสมุด
}
public boolean isEmpty() {//เช็คว่าห้องสมุดว่างหรือไม่
    return booknum.isEmpty(); //คืนค่า true ถ้าห้องสมุดว่าง, false ถ้าไม่ว่าง
}
public int size() {//เช็คจำนวนหนังสือที่จัดเก็บในห้องสมุด
    return booknum.size(); //คืนค่าจำนวนหนังสือที่จัดเก็บในห้องสมุด
}
public int getCapacity() {//เช็คจำนวนพื้นที่ว่างในห้องสมุด
    return capacity; //คืนค่าจำนวนพื้นที่ว่างในห้องสมุด
}
public List<String> getBooks() {//คืนค่าหนังสือทั้งหมดที่จัดเก็บในห้องสมุด
    return new ArrayList<>(booknum); //คืนค่าหนังสือทั้งหมดที่จัดเก็บในห้องสมุด
}

//Producer//
/*คัดลอกรายการหนังสือทั้งหมดที่จัดเก็บในห้องสมุดที่มีรูปแบบเหมือนเดิมทุกประการ */
/**
 * @return
 */
public BondedStack copy() {
    BondedStack copy = new BondedStack(capacity); //สร้างห้องสมุดใหม่ที่มีความจุเท่ากับห้องสมุดเดิม
    for (String book : booknum) { //คัดลอกรายการหนังสือทั้งหมดที่จัดเก็บในห้องสมุด
        copy.push(book); //เพิ่มหนังสือในห้องสมุดใหม่
    }
    return copy; //คืนค่าห้องสมุดใหม่
}
@Override
public String toString() {//คืนค่ารายการหนังสือทั้งหมดที่จัดเก็บในห้องสมุด
    return "BondedStack{" +
            "booknum=" + booknum +
            ", capacity=" + capacity +
            '}';
}
}