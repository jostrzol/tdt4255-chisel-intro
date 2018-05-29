package Core
import chisel3._
import chisel3.core.Input
import chisel3.iotesters.PeekPokeTester

/**
  DaisyGrids hold n daisyVecs. Unlike the daisyVecs, daisyGrids have a select signal for selecting
  which daisyVec to work on, but these daisyVecs can not be controlled from the outside.
  */
class daisyGrid(rows: Int, cols: Int, dataWidth: Int) extends Module{

  val io = IO(new Bundle {

    val readEnable = Input(Bool())
    val dataIn     = Input(UInt(dataWidth.W))
    val rowSelect    = Input(UInt(8.W))

    val dataOut    = Output(UInt(dataWidth.W))
  })

  val currentRowIndex = RegInit(UInt(8.W), 0.U)
  val currentColIndex = RegInit(UInt(8.W), 0.U)

  val memRows = Array.fill(rows){ Module(new daisyVector(cols, dataWidth)).io }
  val elements = rows*cols


  /**
    Your implementation here
    */

  /**
    LF
    */
  io.dataOut := 0.U

  for(ii <- 0 until rows){

    memRows(ii).readEnable := 0.U
    memRows(ii).dataIn := io.dataIn

    when(io.rowSelect === ii.U ){
      memRows(ii).readEnable := io.readEnable
      io.dataOut := memRows(ii).dataOut
    }
  }
}