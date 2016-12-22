import Bool._
import Nat._
import List._

object NatTest {
  implicitly[_2 + _3   =:= _5]

  implicitly[_2 == _2  =:= True]
  implicitly[_2 == _3  =:= False]

  implicitly[_2 =/= _2 =:= False]
  implicitly[_2 =/= _3 =:= True]

  implicitly[_2 <= _3  =:= True]
  implicitly[_3 <= _3  =:= True]
  implicitly[_4 <= _3  =:= False]

  implicitly[_3 >= _3  =:= True]
  implicitly[_3 >= _2  =:= True]
  implicitly[_2 >= _3  =:= False]

  implicitly[_2 < _3   =:= True]
  implicitly[_3 < _3   =:= False]

  implicitly[_4 > _3   =:= True]
  implicitly[_3 > _3   =:= False]

  implicitly[_4 min _3 =:= _3]
  implicitly[_1 min _4 =:= _1]

  implicitly[_4 max _3 =:= _4]
  implicitly[_1 max _4 =:= _4]

  implicitly[_9 * _0   =:= _0]
  implicitly[_0 * _9   =:= _0]
  implicitly[_5 * _1   =:= _5]
  implicitly[_2 * _3   =:= _6]
  implicitly[_4 * _2   =:= _8]
  implicitly[_3 * _3   =:= _9]
}

object BoolTest {
  implicitly[![False]       =:= True]
  implicitly[![True]        =:= False]

  implicitly[True  || False =:= True]
  implicitly[False || False =:= False]

  implicitly[True  && False =:= False]
  implicitly[False && True  =:= False]
  implicitly[True  && True  =:= True]
}

object MapTest {
  type given    = _3 :: _1 :: _2 :: _4 :: _0 :: Nil
  type expected = _4 :: _2 :: _3 :: _5 :: _1 :: Nil
  type result   = given map ({ type F[N <: Nat] = N + _1 })#F

  implicitly[expected =:= result]
}

object FilterTest {
  type given    = _1 :: _2 :: _0 :: _2 :: _3 :: _5 :: _4 :: _1 :: Nil
  type expected = _1 :: _2 :: _0 :: _2 :: _3 :: _1 :: Nil
  type result   = given filter ({ type F[N <: Nat] = N <= _3 })#F

  implicitly[expected =:= result]
}

object FilterNotTest {
  type given    = _1 :: _2 :: _0 :: _2 :: _3 :: _5 :: _4 :: _1 :: Nil
  type expected = _1 :: _2 :: _0 :: _2 :: _3 :: _1 :: Nil
  type result   = given filterNot ({ type F[N <: Nat] = N > _3 })#F

  implicitly[expected =:= result]
}

object RemoveTest {
  type given    = _1 :: _2 :: _3 :: _2 :: Nil
  type expected = _1 :: _2 ::       _2 :: Nil
  type result   = given remove _3

  implicitly[expected =:= result]
}

object SortAscTest {
  type given    = _0 :: _5 :: _3 :: _4 :: _2 :: _1 :: Nil
  type expected = _0 :: _1 :: _2 :: _3 :: _4 :: _5 :: Nil
  type result   = sortAsc[given]

  implicitly[expected =:= result]
}

object SortDescTest {
  type given    = _0 :: _3 :: _4 :: _2 :: _5 :: _1 :: Nil
  type expected = _5 :: _4 :: _3 :: _2 :: _1 :: _0 :: Nil
  type result   = sortDesc[given]

  implicitly[expected =:= result]
}

object ConcatTest {
  type givenLeft  = _0 :: _2 :: _1 :: Nil
  type givenRight =                   _4 :: _3 :: _5 :: Nil
  type expected   = _0 :: _2 :: _1 :: _4 :: _3 :: _5 :: Nil
  type result     = givenLeft ::: givenRight

  implicitly[expected =:= result]
}

object ReverseTest {
  type given    = _0 :: _1 :: _2 :: _3 :: Nil
  type expected = _3 :: _2 :: _1 :: _0 :: Nil
  type result   = reverse[given]

  implicitly[expected =:= result]
}

object SumTest {
  type given    = _0 :: _1 :: _2 :: _3 :: Nil
  type expected = _6
  type result   = sum[given]

  implicitly[expected =:= result]
}

object ProductTest {
  type given    = _1 :: _2 :: _3 :: Nil
  type expected = _6
  // type result   = product[given]

  // implicitly[expected =:= result]
}


object SizeTest {
  type given    = _0 :: _1 :: _2 :: _3 :: Nil
  type expected = _4
  type result   = size[given]

  implicitly[expected =:= result]
}

object ContainsTest {
  type given = _0 :: _1 :: _2 :: _3 :: Nil

  implicitly[given contains _2 =:= True]
  implicitly[given contains _4 =:= False]
}

object ContainsAllTest {
  type given = _0 :: _1 :: _2 :: _3 :: Nil
  type all   = _2 :: _0 :: Nil
  type other = _1 :: _4 :: Nil

  implicitly[given containsAll all   =:= True]
  implicitly[given containsAll other =:= False]
}

object CountTest {
  type given    = _0 :: _1 :: _2 :: _3 :: Nil
  type expected = _2
  type result   = given count ({ type F[N <: Nat] = N >= _2 })#F

  implicitly[expected =:= result]
}

object ExistsTest {
  type given             = _0 :: _1 :: _2 :: _3 :: Nil
  type existsM[M <: Nat] = given exists ({ type F[N <: Nat] = N == M })#F

  implicitly[existsM[_3] =:= True]
  implicitly[existsM[_5] =:= False]
}

object ForallTest {
  type given             = _3 :: _4 :: _5 :: Nil
  type forallM[M <: Nat] = given forall ({ type F[N <: Nat] = M <= N })#F

  implicitly[forallM[_3] =:= True]
  implicitly[forallM[_5] =:= False]
}

object IndexOfTest {
  type given    = _3 :: _4 :: _5 :: Nil
  type expected = _2
  type result   = given indexOf _4

  implicitly[expected =:= result]
}

object EqualTest {
  type given = _0 :: _1 :: _2 :: _3 :: Nil
  type same  = _0 :: _1 :: _2 :: _3 :: Nil
  type other = _0 :: _2 :: _1 :: _3 :: Nil

  implicitly[===[given, same]  =:= True]
  implicitly[===[given, other] =:= False]
}
