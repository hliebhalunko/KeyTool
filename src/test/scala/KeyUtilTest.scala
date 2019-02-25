import java.net.InetAddress
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import main.KeyUtil
import org.scalatest.FunSuite

class KeyUtilTest extends FunSuite {

  /**
    * Test for both KeyUtil.generateKey and KeyUtil.validateKey methods.
    * Tests KeyUtil.generateKey with current machine hostname and current date and KeyUtil.validateKey the result of KeyUtil.generateKey
    */
  test("KeyUtil.generateKey & KeyUtil.validateKey") {
    assert(KeyUtil.validateKey(KeyUtil.generateKey(InetAddress.getLocalHost.getHostName, LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))) === "Valid")
  }

  /**
    * Test for KeyUtil.validateKey with nonsense value
    */
  test("KeyUtil.validateKey with nonsense value") {
    assert(KeyUtil.validateKey("123NONSENSE456") === "Invalid")
  }

  /**
    * Test for KeyUtil.validateKey with empty string
    */
  test("KeyUtil.validateKey with empty string") {
    assert(KeyUtil.validateKey("") === "Invalid")
  }

  /**
    * Test for KeyUtil.validateKey with invalid key
    */
  test("KeyUtil.validateKey with invalid key") {
    assert(KeyUtil.validateKey("lz9vu3qJ2zjmtSBx3lDQ+pVrz1zFvx9BiJNbM+NV+0U=") === "Invalid")
  }
}
