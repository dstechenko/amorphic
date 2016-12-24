package fragile

import fragile.bool._
import fragile.nat._
import fragile.pair._

package object int {
  type Int                     = Pair[Nat]
  type int[N <: Nat]           = N <-> _0
  type eq [L <: Int, R <: Int] = False

  private type isPositive[I <: Int] = firstN[I] > secondN[I]

  type nat[I <: Int]           = ifN[isPositive[I], firstN[I] - secondN[I], _0]

  type canonical[I <: Int]     = ({
                                   type first      = nat[I]
                                   type second     = nat[~[I]]
                                   type run        = first <-> second
                                 })#run


  type ~  [I <: Int]           = secondN[I] <-> firstN[I]

  type -- [L <: Int, R <: Int] = L ++ ~[R]

  type ++ [L <: Int, R <: Int] = ({
                                   type first  = firstN[L] + firstN[R]
                                   type second = secondN[L] + secondN[R]
                                   type run    = canonical[first <-> second]
                                 })#run

  type ** [L <: Int, R <: Int] = ({
                                   type firstLeft   = firstN[L] * firstN[R]
                                   type firstRight  = secondN[L] * secondN[R]
                                   type first       = firstLeft + firstRight
                                   type secondLeft  = firstN[L] * secondN[R]
                                   type secondRight = secondN[L] * firstN[R]
                                   type second      = secondLeft + secondRight
                                   type run         = canonical[first <-> second]
                                 })#run

}