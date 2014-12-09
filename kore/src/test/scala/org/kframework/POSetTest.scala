package org.kframework

import org.junit.Test
import org.junit.Assert._

class POSetTest {
  case class Bar(x: Int)

  import POSet._

  val b1 = Bar(1); val b2 = Bar(2); val b3 = Bar(3); val b4 = Bar(4); val b5 = Bar(5)

  @Test def transitiveness() {
    implicit val p = POSet(b1 -> b2, b2 -> b3, b4 -> b5)

    assertTrue(b1 < b3)
    assertTrue(b1 < b2)
    assertTrue(b1 >= b1)
    assertTrue(b1 <= b1)
    assertTrue(b1 <= b2)
    assertTrue(b1 <= b3)
    assertFalse(b1 < b4)
  }

  @Test def lub() {
    assertEquals(Some(b2), POSet(b1 -> b2).lub)
    assertEquals(Some(b3), POSet(b1 -> b3, b2 -> b3).lub)
    assertEquals(Some(b4), POSet(b1 -> b3, b2 -> b3, b3 -> b4).lub)
    assertEquals(None, POSet(b1 -> b2, b2 -> b3, b4 -> b5).lub)
  }
}
