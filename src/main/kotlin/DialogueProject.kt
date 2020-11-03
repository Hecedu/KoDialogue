//Hector Magana
//Kotlin Dialogue v0.3
//10/28/2020
//A program for generating text adventure dialogue in Kotlin
package  DialogueProject

open class DialogueNode(initialDialogue : String) {
    //lambda expression for show dialogue and choices function
    val dialogueAndChoice = {choice: String, dialogue: String  -> "Choice: $choice /Dialogue: $dialogue" }
    //variables
    var nodeDialogue = initialDialogue
    var childrenDialogueChoiceList = mutableListOf<DialogueNodeChoice>()
    var fakeChoice : DialogueNodeFakeChoice? = null

    //functions
    fun appendDialogueChoice (dialogueChoiceNode: DialogueNodeChoice, dialogueChoice : String ) {
        childrenDialogueChoiceList.add(dialogueChoiceNode)
        dialogueChoiceNode.dialogueChoice = dialogueChoice
    }
    fun setDialogueFakeChoice (dialogueNode: DialogueNodeFakeChoice, fakeChoices : Array<String>) {
        fakeChoice = dialogueNode
        dialogueNode.fakeChoices.addAll(fakeChoices)
    }

    //overloaded functions
    fun removeDialogueOption (dialogueNode: DialogueNode) {
        childrenDialogueChoiceList.remove(dialogueNode)
    }
    fun removeDialogueOption(index: Int) {
        childrenDialogueChoiceList.removeAt(index)
    }
    fun removeFakeDialogue() {
        fakeChoice = DialogueNodeFakeChoice("")
    }

    //visualize children
    fun displayAllDialogueAndChoices(){
        println("Real choices and dialogue nodes they lead to:")
        val dialogueIterator = childrenDialogueChoiceList.iterator()
        while (dialogueIterator.hasNext()){
            val dialogueTemp = dialogueIterator.next()
            println(dialogueAndChoice(dialogueTemp.nodeDialogue,dialogueTemp.dialogueChoice))
        }
        if(fakeChoice != null){
            println("Fake choices and respective node:")
            val fakeDialogueIterator = fakeChoice!!.fakeChoices.iterator()
            while (fakeDialogueIterator.hasNext()){
                println(dialogueAndChoice(fakeChoice!!.nodeDialogue,fakeDialogueIterator.next()))
            }
        }
    }
}

//inherited classes
class DialogueNodeChoice(initialDialogue: String) : DialogueNode(initialDialogue) {
    var dialogueChoice = ""
}

class DialogueNodeFakeChoice (initialDialogue: String) : DialogueNode(initialDialogue) {
    var fakeChoices = mutableListOf<String>()
}

//main Dialogue class (this is the main framework for the text adventure)
class Dialogue(rootDialogueNode: DialogueNode) {
    public var rootDialogue = rootDialogueNode
    private var currentDialogueNode = rootDialogue
    private var choiceBound = 0
    private var fakeBound = 0

    //displays the main dialogue related to the current node
    private fun displayDialogue () {
        println(currentDialogueNode.nodeDialogue)
    }
    //bound setters
    private fun setChoiceBound ( bound : Int) {
        choiceBound = bound
    }
    private fun setFakeBound ( bound : Int) {
        fakeBound = bound
    }
    //display choices that have one to one relations to a dialogue node
    private fun displayChoiceOptions () {

        val dialogueIterator = currentDialogueNode.childrenDialogueChoiceList.iterator()
        if (currentDialogueNode.childrenDialogueChoiceList.isEmpty()) {
            setChoiceBound(0)
        }
        else {
            var i = 1
            while (dialogueIterator.hasNext()) {
                println(i.toString() + ") " + dialogueIterator.next().dialogueChoice)
                i++
            }
            setChoiceBound(i)
        }

    }
    //display choices that have a many to one relation with the main fake node
    private fun displayFakeOptions () {
        if (currentDialogueNode.fakeChoice != null) {
            val fakeIterator = currentDialogueNode.fakeChoice!!.fakeChoices.iterator()
            var i : Int
            if (choiceBound == 0) {
                i = 1
            }
            else {
                i = choiceBound
            }
            while (fakeIterator.hasNext()) {
                println(i.toString() + ") " + fakeIterator.next())
                i++
            }
            setFakeBound(i)
        }
    }
    //main display call
    private fun displayAllOptions () {
        displayChoiceOptions()
        displayFakeOptions()
    }

    //main game function
    fun traverseDialogue ()
    {
        displayDialogue()
        if (currentDialogueNode.childrenDialogueChoiceList.isEmpty() && currentDialogueNode.fakeChoice == null) {
            println("Game Over")
            return
        }
        else {
            displayAllOptions()
            //range in if statement
            when (val userChoice = Integer.valueOf(readLine())) {

                0 -> {
                    println("out of bounds, try another input")
                    traverseDialogue()
                }
                in 0..choiceBound -> {
                    currentDialogueNode = currentDialogueNode.childrenDialogueChoiceList[userChoice - 1]
                    traverseDialogue()
                }
                in (choiceBound)..fakeBound -> {
                    currentDialogueNode = currentDialogueNode.fakeChoice!!
                    traverseDialogue()
                }
                else -> {
                    println("out of bounds, try another input")
                    traverseDialogue()
                }
            }
        }
    }

}
fun main(args: Array<String>) {
    //set the outcomes of each choice of the adventure

    val initNode = DialogueNode("You encounter a beast, it is gigantic, has 4 eyes and multiple horns.")
        val useBruteForce = DialogueNodeFakeChoice("The beast rips you to pieces.")
        initNode.setDialogueFakeChoice(useBruteForce, arrayOf("Fight the beast with all your might","Attempt to slash beast with your best sword"))
        val fleeingFromBeast = DialogueNodeChoice("You run from the beast.")
        initNode.appendDialogueChoice(fleeingFromBeast,"Run from the beast as fast as possible.")
        val talkToBeast = DialogueNodeChoice("The beast surprisingly can understand human language. It asks you about your prayer")
        initNode.appendDialogueChoice(talkToBeast,"Pray to your god for protection.")
            val talkAboutGod = DialogueNodeChoice("The beast congratulates you on your bravery and asks you to keep on having faith on your war god and go home.")
            talkToBeast.appendDialogueChoice(talkAboutGod, "Talk to him about Lusitanian, your god of war")
            val denyToTalk = DialogueNodeChoice("The beast applauds your courage: 'I've never met a human like you!'")
            talkToBeast.appendDialogueChoice(denyToTalk,"Don't say anything to the beast.")
            val inquireAboutLanguage = DialogueNodeChoice("The beast replies: 'Long ago a human warrior taught me how to talk, he reminds me of you'")
            talkToBeast.appendDialogueChoice(inquireAboutLanguage, "Ask the beast about his language skills.")
        val useMagicAgainstBeast = DialogueNodeChoice("The beast emits a terrifying scream and becomes immobilized, you have a chance to act.")
        initNode.appendDialogueChoice(useMagicAgainstBeast,"Use a blinding spell on the beast.")
            val curseAcquired = DialogueNodeFakeChoice("Lusitanian voice resonates on the cave: 'Coward!! Don't ever dare to call yourself a warrior again!!' \nA curse mark appears on your forehead")
            useMagicAgainstBeast.setDialogueFakeChoice(curseAcquired, arrayOf("Stab beast on the back while it is immobilized,","Taunt the beast and cut it's tail.","Steal the beast treasure while it is stunned."))

    initNode.displayAllDialogueAndChoices()
    //initialize dialogue game
    val dialogue = Dialogue(initNode)

    //play game
    dialogue.traverseDialogue()

}



