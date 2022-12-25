package year2021.day16

import model.DaySolver

class Day16Solver : DaySolver<String, Int> {

    private fun parseData(data: String): String {
        return data.split("").map { x-> x.trim() }.filter { it.isNotBlank() }.map { x -> x.toInt(16).toString(2).padStart(4,'0') }.joinToString("")
    }

    override fun part1(data: String): Int {
        println(data)
        println(parseData(data))
        return parsePacket(parseData(data)).first
    }

    private fun parsePacket(packet:String): Pair<Int, String> {
        if(packet.all { x -> x == '0' }) {
            return Pair(0,"")
        }
        println("packet is ${packet}")
        var version = packet.substring(0,3).toInt(2)
        val packetId = packet.substring(3,6).toInt(2)
        println("version is ${version}")
        println("packet id is ${packetId}")
        if (packetId == 4) {
            var temp = packet.substring(6)
            while (temp[0] != '0') {
                temp = temp.substring(5)
            }
            temp = temp.substring(5)
            println("temp is $temp")
            return Pair(version, temp)
        } else {
            if (packet[6] == '0') {
                var packetLength = packet.substring(7,7+15).toInt(2)
                var temp = packet.substring(7+15)
                while (packetLength > 0){
                    val (newVersion, remPacket) = parsePacket(temp)
                    packetLength -= temp.length - remPacket.length
                    temp = remPacket
                    version+= newVersion
                }
                return Pair(version, temp)
            }else {
                var subPackets = packet.substring(7, 7 + 11).toInt(2)
                var temp = packet.substring(7+11)
                while (subPackets > 0){
                    val (newVersion, remPacket) = parsePacket(temp)
                    version += newVersion
                    temp = remPacket
                    subPackets--
                }
                return Pair(version,temp)
            }
        }
    }

    private fun parsePacket1(packet:String): Pair<Long, String> {
        if(packet.all { x -> x == '0' }) {
            return Pair(0,"")
        }
        val packetId = packet.substring(3,6).toInt(2)
        if (packetId == 4) {
            var temp = packet.substring(6)
            var value = temp.substring(1,5)
            while (temp[0] != '0') {
                temp = temp.substring(5)
                value += temp.substring(1,5)
            }
            temp = temp.substring(5)
            return Pair(value.toLong(2), temp)
        } else {
            if (packet[6] == '0') {
                var packetLength = packet.substring(7,7+15).toInt(2)
                var temp = packet.substring(7+15)
                val values = mutableListOf<Long>()
                while (packetLength > 0){
                    val (value, remPacket) = parsePacket1(temp)
                    packetLength -= temp.length - remPacket.length
                    temp = remPacket
                    values.add(value)
                }
                println("values $values")
                return Pair(findFinalValue(values, packetId), temp)
            }else {
                var subPackets = packet.substring(7, 7 + 11).toInt(2)
                var temp = packet.substring(7+11)
                val values = mutableListOf<Long>()
                while (subPackets > 0){
                    val (value, remPacket) = parsePacket1(temp)
                    values.add(value)
                    temp = remPacket
                    subPackets--
                }
                println("values $values")
                return Pair(findFinalValue(values,packetId),temp)
            }
        }
    }

    private fun findFinalValue(values: List<Long>, packetId: Int): Long {
        return when(packetId){
            0 -> values.sum()
            1 -> values.fold(1){ acc, i -> acc * i}
            2 -> values.minOrNull()!!
            3 -> values.maxOrNull()!!
            5 -> if (values[0] > values[1]) 1 else 0
            6 -> if (values[0] < values[1]) 1 else 0
            else -> if (values[0] == values[1]) 1 else 0
        }
    }

    override fun part2(data: String): Int {
        println(parsePacket1(parseData(data)).first)
        return 0
    }
}