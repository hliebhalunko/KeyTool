package main


import java.net.InetAddress
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Base64

import javax.crypto.Cipher
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}

/**
  * Class that is responsible for generation and validation of keys.
  */

object KeyUtil {

  private val Algorithm = "AES/CBC/PKCS5Padding"
  private val Key = new SecretKeySpec(Base64.getDecoder.decode("BAmBST68JOlAmanaf1+jDw=="), "AES")
  private val IvSpec = new IvParameterSpec(new Array[Byte](16))
  private val Formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

  /**
    * Method that generates key based on input parameters.
    *
    * @param hostname       - name of the host machines that are allowed to use software.
    * @param expirationDate - license expiration date in format dd-MM-yyyy
    * @return - key
    */

  def generateKey(hostname: String, expirationDate: String): String = {

    val cipher = Cipher.getInstance(Algorithm)
    cipher.init(Cipher.ENCRYPT_MODE, Key, IvSpec)

    val date = LocalDate.parse(expirationDate, Formatter)
    val text = hostname + "&" + date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))

    new String(Base64.getEncoder.encode(cipher.doFinal(text.getBytes("utf-8"))), "utf-8")

  }

  /**
    * Method responsible for keys validation.
    *
    * @param key - key
    * @return "Valid" if key is valid, otherwise "Invalid"
    */

  def validateKey(key: String): String = {

    val cipher = Cipher.getInstance(Algorithm)
    cipher.init(Cipher.DECRYPT_MODE, Key, IvSpec)

    var hostname: String = ""
    var date: String = ""

    try {
      val str = new String(cipher.doFinal(Base64.getDecoder.decode(key.getBytes("utf-8"))), "utf-8")
      hostname = str.split("&")(0)
      date = str.split("&")(1)
      if (LocalDate.parse(date, Formatter).compareTo(LocalDate.now()) >= 0 && InetAddress.getLocalHost.getHostName.contains(hostname)) {
        return "Valid"
      }
    } catch {
      case _: Exception => return "Invalid"
    }

    "Invalid"

  }

}
