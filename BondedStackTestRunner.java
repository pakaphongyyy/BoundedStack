public class BondedStackTestRunner {

    // นับจำนวน Test ที่ผ่าน
    private static int pass = 0;

    // นับจำนวน Test ที่ไม่ผ่าน
    private static int fail = 0;

    /**
     * จุดเริ่มต้นของโปรแกรม
     * เรียกใช้เมธอดทดสอบทุกตัว
     */
    public static void main(String[] args) {

        testConstructor();
        testPush();
        testPop();
        testPeek();
        testIsEmpty();
        testSize();
        testOverflow();
        testPopEmpty();
        testDuplicateBook();
        testInvalidBook();
        testCopy();

        // แสดงผลสรุปทั้งหมด
        System.out.println("\n==============================");
        System.out.println("Total Test : " + (pass + fail));
        System.out.println("PASS       : " + pass);
        System.out.println("FAIL       : " + fail);
        System.out.println("==============================");
    }

    /**
     * เมธอดสำหรับตรวจสอบผลการทดสอบ
     *
     * @param condition ผลลัพธ์ที่ต้องการตรวจสอบ
     * @param testName ชื่อของ Test Case
     */
    private static void check(boolean condition, String testName) {

        // ถ้าเงื่อนไขเป็นจริง แสดง PASS
        if (condition) {
            System.out.println("PASS : " + testName);
            pass++;
        }

        // ถ้าเงื่อนไขเป็นเท็จ แสดง FAIL
        else {
            System.out.println("FAIL : " + testName);
            fail++;
        }
    }

    /**
     * ทดสอบ Constructor
     *
     * ตรวจสอบว่าเมื่อสร้าง Stack
     * จะมี Capacity ตรงกับค่าที่กำหนด
     */
    private static void testConstructor() {

        BondedStack stack = new BondedStack(3);

        check(stack.getCapacity() == 3,
                "Constructor");
    }

    /**
     * ทดสอบการเพิ่มหนังสือ (push)
     *
     * ตรวจสอบว่า
     * 1. จำนวนหนังสือเพิ่มขึ้น
     * 2. หนังสือที่อยู่บนสุดถูกต้อง
     */
    private static void testPush() {

        BondedStack stack = new BondedStack(3);

        stack.push("10");

        check(stack.size() == 1 &&
                stack.peek().equals("10"),
                "Push");
    }

    /**
     * ทดสอบการนำหนังสือออก (pop)
     *
     * Stack ต้องทำงานแบบ LIFO
     * คือหนังสือที่ใส่ล่าสุดต้องถูกนำออกก่อน
     */
    private static void testPop() {

        BondedStack stack = new BondedStack(3);

        stack.push("10");
        stack.push("20");

        String value = stack.pop();

        check(
                value.equals("20") &&
                        stack.size() == 1 &&
                        stack.peek().equals("10"),
                "Pop");
    }

    /**
     * ทดสอบ Peek
     *
     * ตรวจสอบว่า Peek
     * คืนค่าหนังสือบนสุด
     * โดยไม่ลบหนังสือออกจาก Stack
     */
    private static void testPeek() {

        BondedStack stack = new BondedStack(3);

        stack.push("99");

        check(stack.peek().equals("99"),
                "Peek");
    }

    /**
     * ทดสอบ isEmpty()
     *
     * ก่อนเพิ่มหนังสือ
     * Stack ต้องว่าง
     *
     * หลังเพิ่มหนังสือ
     * Stack ต้องไม่ว่าง
     */
    private static void testIsEmpty() {

        BondedStack stack = new BondedStack(3);

        boolean before = stack.isEmpty();

        stack.push("1");

        boolean after = stack.isEmpty();

        check(before && !after,
                "isEmpty");
    }

    /**
     * ทดสอบ size()
     *
     * ตรวจสอบว่าจำนวนหนังสือ
     * ตรงกับจำนวนที่เพิ่มเข้าไป
     */
    private static void testSize() {

        BondedStack stack = new BondedStack(5);

        stack.push("1");
        stack.push("2");
        stack.push("3");

        check(stack.size() == 3,
                "Size");
    }

    /**
     * ทดสอบกรณี Stack เต็ม
     *
     * ต้องเกิด IllegalStateException
     */
    private static void testOverflow() {

        BondedStack stack = new BondedStack(2);

        try {

            stack.push("1");
            stack.push("2");
            stack.push("3");

            // ถ้าไม่เกิด Exception ถือว่าผิด
            check(false,
                    "Overflow");

        } catch (IllegalStateException e) {

            // ถ้าเกิด Exception ถือว่าถูกต้อง
            check(true,
                    "Overflow");
        }
    }

    /**
     * ทดสอบ Pop จาก Stack ว่าง
     *
     * ต้องเกิด IllegalStateException
     */
    private static void testPopEmpty() {

        BondedStack stack = new BondedStack(2);

        try {

            stack.pop();

            check(false,
                    "Pop Empty");

        } catch (IllegalStateException e) {

            check(true,
                    "Pop Empty");
        }
    }

    /**
     * ทดสอบการเพิ่มหนังสือซ้ำ
     *
     * ต้องเกิด IllegalArgumentException
     */
    private static void testDuplicateBook() {

        BondedStack stack = new BondedStack(3);

        try {

            stack.push("5");
            stack.push("5");

            check(false,
                    "Duplicate Book");

        } catch (IllegalArgumentException e) {

            check(true,
                    "Duplicate Book");
        }
    }

    /**
     * ทดสอบการเพิ่มหนังสือที่ไม่ถูกต้อง
     *
     * เช่น หมายเลขเกิน 500
     * ต้องเกิด IllegalArgumentException
     */
    private static void testInvalidBook() {

        BondedStack stack = new BondedStack(3);

        try {

            stack.push("600");

            check(false,
                    "Invalid Book");

        } catch (IllegalArgumentException e) {

            check(true,
                    "Invalid Book");
        }
    }

    /**
     * ทดสอบเมธอด copy()
     *
     * ตรวจสอบว่า
     * 1. Object ใหม่ไม่ใช่ Object เดิม
     * 2. จำนวนหนังสือเท่ากัน
     * 3. หนังสือบนสุดเหมือนกัน
     */
    private static void testCopy() {

        BondedStack stack = new BondedStack(5);

        stack.push("1");
        stack.push("2");

        BondedStack copy = stack.copy();

        boolean result =
                copy != stack &&
                copy.size() == stack.size() &&
                copy.peek().equals(stack.peek());

        check(result,
                "Copy");
    }

}
