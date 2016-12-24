package fragile

import fragile.bool._
import fragile.nat._
import fragile.product._

import language.higherKinds

package object list {
  type list                 [N <: Nat]                                          = N :: Nil
  type map                  [L <: List, F[_ <: Nat] <: Nat]                     = L#Map[F]
  type flatMap              [L <: List, F[_ <: Nat] <: List]                    = L#FlatMap[F]
  type filter               [L <: List, F[_ <: Nat] <: Bool]                    = L#Filter[F]
  type fold                 [L <: List, N <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] = L#Fold[N, F]
  type indexOf              [L <: List, N <: Nat]                               = L#IndexOf[N, _0]
  type remove               [L <: List, N <: Nat]                               = L#Remove[N]
  type :::                  [L <: List, R <: List]                              = L#Concat[R]
  type reverse              [L <: List]                                         = L#Reverse
  type selectSort           [L <: List]                                         = L#SelectSort
  type quickSort            [L <: List]                                         = L#QuickSort
  type size                 [L <: List]                                         = L#Size
  type takeLeft             [L <: List, N <: Nat]                               = L#TakeLeft[N]
  type applyOrElse          [L <: List, N <: Nat, E <: Nat]                     = L#ApplyOrElse[N, E]

  type isEmpty              [L <: List]                                         = size[L] == _0
  type nonEmpty             [L <: List]                                         = ![isEmpty[L]]
  type reduceP              [L <: List, F[_ <: Nat, _ <: Nat] <: Nat]           = fold[L, _0, F]
  type reduceM              [L <: List, F[_ <: Nat, _ <: Nat] <: Nat]           = fold[L, _1, F]
  type sum                  [L <: List]                                         = L reduceP ({ type F[LN <: Nat, RN <: Nat] = RN + LN })#F
  type product              [L <: List]                                         = L reduceM ({ type F[LN <: Nat, RN <: Nat] = RN * LN })#F
  type count                [L <: List, F[_ <: Nat] <: Bool]                    = size[L filter F]
  type contains             [L <: List, N <: Nat]                               = (L indexOf N) > _0
  type containsAll          [L <: List, R <: List]                              = R forall ({ type F[N <: Nat] = L contains N })#F
  type exists               [L <: List, F[_ <: Nat] <: Bool]                    = (L count F) > _0
  type filterNot            [L <: List, F[_ <: Nat] <: Bool]                    = L filter ({ type FN[N <: Nat] = ![F[N]] })#FN
  type forall               [L <: List, F[_ <: Nat] <: Bool]                    = ![L exists ({ type FN[N <: Nat] = ![F[N]] })#FN]
  type selectSortAsc        [L <: List]                                         = selectSort[L]
  type selectSortDesc       [L <: List]                                         = reverse[selectSortAsc[L]]
  type quickSortAsc         [L <: List]                                         = quickSort[L]
  type quickSortDesc        [L <: List]                                         = reverse[quickSortAsc[L]]
  type corresponds          [L <: List, R <: List]                              = L forall ({ type F[N <: Nat] = (L indexOf N) == (R indexOf N) })#F
  type ===                  [L <: List, R <: List]                              = (size[L] == size[R]) && (L corresponds R) && (R corresponds L)
  type isDefinedAt          [L <: List, N <: Nat]                               = size[L] >= N
  type distinct             [L <: List]                                         = reverse[reverse[L]#Distinct]
  type isDistinct           [L <: List]                                         = L === distinct[L]
  type removeEvery          [L <: List, M <: Nat]                               = L filterNot ({ type F[N <: Nat] = M == N })#F
  type removeAll            [L <: List, R <: List]                              = L filterNot ({ type F[N <: Nat] = R contains N })#F
  type dropLeft             [L <: List, N <: Nat]                               = reverse[reverse[L] takeLeft (size[L] - N)]
  type dropRight            [L <: List, N <: Nat]                               = L takeLeft (size[L] - N)
  type takeRight            [L <: List, N <: Nat]                               = L dropLeft (size[L] - N)
  type startsWith           [L <: List, R <: List]                              = (L takeLeft size[R]) === R
  type startsWithOffset     [L <: List, R <: List, B <: Nat]                    = (L dropLeft B) startsWith R
  type endsWith             [L <: List, R <: List]                              = (L takeRight size[R]) === R
  type slice                [L <: List, B <: Nat, E <: Nat]                     = (L dropLeft (B - _1)) dropRight (size[L] - E)
  type union                [L <: List, R <: List]                              = L ::: R
  type diff                 [L <: List, R <: List]                              = L filterNot ({ type F[N <: Nat] = R contains N })#F
  type indexWhere           [L <: List, F[_ <: Nat] <: Bool]                    = applyOrElse[(L filter F) map ({ type F[N <: Nat] = (L indexOf N) })#F, _1, _0]
  type indexWhereFrom       [L <: List, F[_ <: Nat] <: Bool, B <: Nat]          = ifN[(L dropLeft B) indexWhere F == _0, _0, ((L dropLeft B) indexWhere F) + B]
  type indexOfUntil         [L <: List, M <: Nat, E <: Nat]                     = (L takeLeft E) indexWhere ({ type F[N <: Nat] = N == M })#F
  type lastIndexOfUntil     [L <: List, N <: Nat, E <: Nat]                     = (L takeLeft E) lastIndexOf N

  type lastIndexOf          [L <: List, N <: Nat]                               = ({
                                                                                    type index = size[L] - (reverse[L] indexOf N) + _1
                                                                                    type run   = ifN[L contains N, index, _0]
                                                                                  })#run

  type countWhile           [L <: List, F[_ <: Nat] <: Bool]                    = L indexWhere ({ type G[N <: Nat] = ![F[N]] })#G - _1
  type takeWhile            [L <: List, F[_ <: Nat] <: Bool]                    = L takeLeft (L countWhile F)
  type dropWhile            [L <: List, F[_ <: Nat] <: Bool]                    = L dropLeft (L countWhile F)
  type partition            [L <: List, F[_ <: Nat] <: Bool]                    = product2[L filter F, L filterNot F]





  type intersectFM          [L <: List, R <: List]                              = L flatMap ({ type F[N <: Nat] = ifL[R contains N, list[N], Nil] })#F
  type intersect            [L <: List, R <: List]                              = L filter ({ type F[N <: Nat] = R contains N })#F


  type indexOfSlice         [L <: List, R <: List]                              = ({
                                                                                    type source                     = reverse[L]
                                                                                    type target                     = reverse[R]
                                                                                    type visit [_ <: Nat, N <: Nat] = ifN[startsWithOffset[source, target, N - _1], N, N + _1]
                                                                                    type index                      = source reduceM visit
                                                                                    type run                        = size[L] - index - size[R]
                                                                                  })#run

  type indexOfSliceFrom     [L <: List, R <: List, B <: Nat]                    <: List
  type containsSlice        [L <: List, R <: List]                              = (L indexOfSlice R) > _0
  type removeSlice          [L <: List, R <: List]                              <: List

  type lastIndexOfSlice     [L <: List, R <: List]                              <: Nat
  type lastIndexOfSliceUntil[L <: List, R <: Nat, E <: Nat]                     <: Nat

  type lastIndexOfWhere     [L <: List, F[_ <: Nat] <: Bool]                    <: Nat
  type lastIndexOfWhereUntil[L <: List, F[_ <: Nat] <: Bool, E <: Nat]          <: Nat

  type padTo                [L <: List, N <: Nat, E <: Nat]                     <: List

  type permutations         [L <: List]                                         <: List
}
