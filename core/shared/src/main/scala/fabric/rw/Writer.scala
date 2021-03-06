package fabric.rw

import fabric._

/**
 * Writable provides a simple Value => T wrapper functionality
 */
trait Writer[T] {
  def write(value: Value): T
}

object Writer {
  import ReaderWriter._

  implicit def boolW: Writer[Boolean] = boolRW

  implicit def shortR: Writer[Short] = shortRW
  implicit def intW: Writer[Int] = intRW
  implicit def longW: Writer[Long] = longRW
  implicit def floatW: Writer[Float] = floatRW
  implicit def doubleW: Writer[Double] = doubleRW
  implicit def bigIntW: Writer[BigInt] = bigIntRW
  implicit def bigDecimalW: Writer[BigDecimal] = bigDecimalRW

  implicit def stringW: Writer[String] = stringRW

  implicit def listW[T](implicit w: Writer[T]): Writer[List[T]] = apply[List[T]] {
    case Arr(vector) => vector.toList.map(w.write)
    case v => throw new RuntimeException(s"Unsupported list: $v")
  }
  implicit def optionW[T](implicit w: Writer[T]): Writer[Option[T]] = apply[Option[T]] {
    case Null => None
    case v => Option(w.write(v))
  }

  def apply[T](f: Value => T): Writer[T] = new Writer[T] {
    override def write(value: Value): T = f(value)
  }
}