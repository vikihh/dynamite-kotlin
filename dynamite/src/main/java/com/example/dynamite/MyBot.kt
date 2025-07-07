package com.example.dynamite

import com.softwire.dynamite.bot.Bot
import com.softwire.dynamite.game.Gamestate
import com.softwire.dynamite.game.Move
import com.softwire.dynamite.game.Round
import com.softwire.dynamite.opponents.RandomRPSBot

class MyBot : Bot {
    var round = 0;

    var theirD = 0;
    var theirW = 0;
    var theirP = 0;
    var theirS = 0;
    var theirR = 0;

    var myD = 0;
    var myW = 0;
    var myP = 0;
    var myS = 0;
    var myR = 0;


    var theirLast = ""
    var myLast = ""

    var theirDConsecutive = 0
    var theirWConsecutive = 0
    var theirPConsecutive = 0
    var theirRConsecutive = 0
    var theirSConsecutive = 0
    var theirRPSConsecutive = 0

    var dynOnDraw = emptyArray<Int>()
    var watOnDraw = emptyArray<Int>()
    var rspOnDraw = emptyArray<Int>()///last 200 draws
    var numDraws = 200
    var flag = false
    var dyns = (1..1500).shuffled().take(100).sorted()
    var i =0
    fun numOfLast200draws(prefDraws: Array<Int>):Int
    {
        if (prefDraws.size<200) return prefDraws[prefDraws.size-1]
        return prefDraws[prefDraws.size-1] - prefDraws[prefDraws.size-50]
    }
    var i1 = 0
    var i2 = 0
    var i3 = 0
    override fun makeMove(gamestate: Gamestate): Move {
        // Are you debugging?


        if (round == 0) {round ++; myLast = "D"; myD++; return Move.D}
        var rnd = (1..3).shuffled().first()


        var Last1 = gamestate.getRounds()[gamestate.getRounds().size - 1].getP1().name;
        var Last2 = gamestate.getRounds()[gamestate.getRounds().size - 1].getP2().name

        if (Last1 ==myLast) theirLast = Last2
        else theirLast = Last1
        if (flag)
        {
            when (theirLast)
            {
                "D" -> {dynOnDraw += (dynOnDraw[i1] + 1); i1++}
                "W" -> {watOnDraw += (watOnDraw[i2] + 1); i2++}
                else -> {rspOnDraw += (rspOnDraw[i3] + 1); i3++}
            }
        }
        theirRPSConsecutive ++
        if (theirLast == "W") {theirW ++; theirWConsecutive++; theirRPSConsecutive = 0}
        else theirWConsecutive = 0
        if (theirLast == "D") {theirD ++; theirDConsecutive ++; theirRPSConsecutive = 0}
        else theirDConsecutive = 0
        if (theirLast == "R") {theirR ++; theirRConsecutive ++}
        else theirRConsecutive = 0
        if (theirLast == "P") {theirP ++; theirPConsecutive ++}
        else theirPConsecutive = 0
        if (theirLast == "S") {theirS ++;  theirSConsecutive ++}
        else theirSConsecutive = 0


        if (myLast == "W") myW ++;
        //if (myLast == "D") {myD ++; myDList += (round -1);}
        if (myLast == "R") myR ++;
        if (myLast == "P") myP ++;
        if (myLast == "S")myS ++;
        round ++;


        flag = false
        if (theirLast == myLast)
        {
            flag = true;
            var dynDr= numOfLast200draws(dynOnDraw)
            var watDr= numOfLast200draws(watOnDraw)
            var rspDr= numOfLast200draws(rspOnDraw)
            if ( ((dynDr>= watDr && dynDr >=rspDr) ||(theirDConsecutive>2))  && theirD<100)//((dynDr>= watDr && dynDr >=rspDr) ||
            {myLast = "W";return Move.W}
            if (watDr >= dynDr && watDr >=rspDr)
            {
                if (rnd == 1) {myLast = "R";return Move.R}
                if (rnd == 2) {myLast = "P";return Move.P}
                if (rnd == 3) {myLast = "S";return Move.S}

            }
            if (rspDr >= watDr && rspDr >= dynDr)
            {
                if (myD < 100 && theirDConsecutive<3 && theirD<100)
                {
                    myD++;
                    myLast = "D";
                    i++
                    return Move.D
                }
                else
                {
                    var n = 100
                    if ((theirDConsecutive>=2)) n+=60
                    if (myD>=100) n+=60
                    if (theirD>50) n-=5
                    if (theirD<90) n=90
                    //{myLast = "W";return Move.W}

                    rnd = (1..n).shuffled().first()
                    if (rnd <= 30) {myLast = "R";return Move.R}
                    if (rnd <= 60) {myLast = "P";return Move.P}
                    if (rnd <= 90) {myLast = "S";return Move.S}
                    if (rnd >90) {myLast = "W";return Move.W}
                }
            }
        }
        if (theirRConsecutive>50) {myLast = "P";return Move.P}
        if (theirPConsecutive>50) {myLast = "S";return Move.S}
        if (theirSConsecutive>50) {myLast = "R";return Move.R}

       // if (theirDConsecutive>5) {myLast = "W"; return Move.W}
            /* if (i<100 && round>dyns[i]) i++; //println(round)}
        if (i<100 && round == dyns[i]) {
            i++
          /*  print(i-1)
            print(" - ")
            print(dyns[i])
            print(", ")*/
            if (myD < 100)
            {
                myD++;
                myLast = "D";
                return Move.D
            }
            else
            {
                rnd = (1..140).shuffled().first()
                if (rnd <= 30) {myLast = "R";return Move.R}
                if (rnd <= 60) {myLast = "P";return Move.P}
                if (rnd <= 90) {myLast = "S";return Move.S}
                if (rnd >90) {myLast = "W";return Move.W}
            }
        }*/

        if (theirRPSConsecutive > 3)
        {

            if (rnd == 1) {myLast = "R";return Move.R}
            if (rnd == 2) {myLast = "P";return Move.P}
            if (rnd == 3) {myLast = "S";return Move.S}

        }

        if (theirDConsecutive>4 && theirD<100) {myLast = "W";return Move.W}
        if (theirWConsecutive>3) {myLast = "R";return Move.R}

        if (rnd == 1) {myLast = "R";return Move.R}
        if (rnd == 2) {myLast = "P";return Move.P}
        if (rnd == 3) {myLast = "S";return Move.S}
       // rnd = (2..4).shuffled().first()
       // rnd = (1..3).shuffled().first()

        // Put a breakpoint in this method to see when we make a move
        myLast = "R";
        return Move.R

    }

    init {

        dynOnDraw += 0
        watOnDraw += 0
        rspOnDraw += 1


        // Are you debugging?
        // Put a breakpoint on the line below to see when we start a new match
        println("Started new match")
    }
}
