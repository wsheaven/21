class Player() {

    var money = 10
    var bust = false

    val cards = arrayOf(
        "Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
        "Jack", "Queen", "King"
    )

    val cardWorth = mapOf(
        "Ace" to 1, "Two" to 2, "Three" to 3, "Four" to 4, "Five" to 5, "Six" to 6, "Seven" to 7,
        "Eight" to 8, "Nine" to 9, "Ten" to 10, "Jack" to 10, "Queen" to 10, "King" to 10
    )

    val hand = mutableListOf<String>()

    init {
        getCard()
        getCard()
    }

    fun reset(){
        hand.clear()
        getCard()
        getCard()
    }

    fun getCard() {
        var card = (0..12).random()
        hand.add(cards[card])
    }

    fun playerTotal(): Int {
        var total = 0
        for (cardss in hand) {
            var fish = true
            if (cardss == "Ace") {
                while (fish) {
                    println("Is this Ace an 11 or a 1? (enter 1 or 11) ")
                    val stringInput = readLine()!!
                    if (stringInput == "11") {
                        total += 11
                        fish = false
                    } else if (stringInput == "1") {
                        total += 1
                        fish = false
                    }
                }

            } else {
                total += cardWorth["$cardss"]!!
            }


        }
        return total
    }

    fun bust(): Boolean {
        return playerTotal() > 21
    }

}

class Dealer() {

    val cards = arrayOf(
        "Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
        "Jack", "Queen", "King"
    )

    val cardWorth = mapOf(
        "Ace" to 1, "Two" to 2, "Three" to 3, "Four" to 4, "Five" to 5, "Six" to 6, "Seven" to 7,
        "Eight" to 8, "Nine" to 9, "Ten" to 10, "Jack" to 10, "Queen" to 10, "King" to 10
    )

    val hand = mutableListOf<String>()

    var bust = false

    init {
        getCard()
        getCard()
    }

    fun reset(){
        hand.clear()
        getCard()
        getCard()
    }

    fun getCard(): String {
        var card = (0..12).random()
        hand.add(cards[card])
        return cards[card]
    }

    fun playerTotal(): Int {
        var total = 0
        for (cardss in hand) {
            var fish = true
            if (cardss == "Ace") {
                while (fish) {
                    if (total + 11 > 21) {
                        total += 1
                        fish = false
                    } else {
                        total += 11
                        fish = false
                    }
                }

            } else {
                total += cardWorth["$cardss"]!!
            }


        }
        return total
    }

    fun bust(): Boolean {
        return playerTotal() > 21
    }

}


 class Game(dealer: Dealer, player: Player) {

     var player = player
     var dealer = dealer
     var playerBet = 0
     var progress = 0


     fun test() {
         println("Player hand is " + player.hand)
         println("Player total is " + player.playerTotal())

         println("Dealer hand is " + dealer.hand)
         println("Dealer total is " + dealer.playerTotal())
     }


     fun play(): Boolean {
         var go = true
         while (go) {
             println("Would you like to hit? (enter 'hit' or 'hold') ")
             val playerAction = readLine()!!
             if (playerAction == "hit") {
                 println(player.getCard())
                 println("Your new total is "+ player.playerTotal())
                 go = false
                 return true
             } else if (playerAction == "hold") {
                 go = false
                 return false
             }

         }
         return false
     }

     fun dealerPlay(): Boolean {
         if (dealer.playerTotal() < 16){
             println("The dealer hit and got a " + dealer.getCard())
             return true
         }
         else {
             println("The dealer did not hit ")
             return false
         }
     }

     fun bet(){
         var betGo = true
         while (betGo) {
             println("You have $" + player.money)
             println("How much would you like to bet? ")
             playerBet = readLine()!!.toInt()
             if (playerBet <= player.money){
                 betGo = false
             }
             else {
                 println("You do not have that much money to bet!")
             }
         }
     }

     fun main(){

         // Ask the player about betting
         bet()

         // set up the game
         println("The dealer is showing: " + dealer.hand[0])
         println("Your hand is: " + player.hand)
         println("Your total is " + player.playerTotal())


         var playerGo = true
         var dealerGo = true

         while(playerGo or dealerGo) {
             playerGo = play()

             dealerGo = dealerPlay()

         }
         println("The dealer total is " + dealer.playerTotal())
         println("Your total is " + player.playerTotal())

         if (player.bust()){
             println("You got over 21 and busted! ")
             player.money -= playerBet
             println("You lost $"+ playerBet + " and you now have $" + player.money)
         }
         else if (dealer.bust()){
             println("The dealer got over 21 and busted! ")
             player.money += playerBet
             println("You won $"+ playerBet + " and you now have $" + player.money)
         }
         else if (player.playerTotal() <= dealer.playerTotal()){
             println("The dealer wins! ")
             player.money -= playerBet
             println("You lost $"+ playerBet + " and you now have $" + player.money)
         }

         else if (player.playerTotal() > dealer.playerTotal()){
             println("You win! ")
             player.money += playerBet
             println("You won $"+ playerBet + " and you now have $" + player.money)
         }

         println("Would you like to keep playing? (y/n) ")
         var keepPlaying = readLine()!!

         if (keepPlaying == "n"){
             println("You finished the game with $" + player.playerTotal() + "you made $" + progress)
             player.money = 0
             return
         }

         player.reset()
         dealer.reset()

     }
}




fun main(args: Array<String>) {

    var dealer = Dealer()
    var player = Player()



    println()

    val game = Game(dealer, player)

    while (player.money > 0){
        game.main()
    }

    println("Goodbye!")



}