//Hector Magana
//Kotlin Dialogue v0.3
//10/28/2020
//A program for generating text adventure dialogue in Kotlin
package  DialogueProject

class DialogueNode(initialDialogue : String) {
    var nodeDialogue = initialDialogue
    var childrenDialogueList = mutableListOf<DialogueNode>()
    var dialogueChoices = mutableListOf<String>()

    fun appendDialogue (dialogueNode: DialogueNode, dialogueChoice : String ) {
        childrenDialogueList.add(dialogueNode)
        dialogueChoices.add(dialogueChoice)
    }
    fun removeDialogueOption (dialogueNode: DialogueNode,dialogueChoice : String) {
        childrenDialogueList.remove(dialogueNode)
        dialogueChoices.remove(dialogueChoice)
    }



}
class Dialogue(rootDialogueNode: DialogueNode) {
    var rootDialogue = rootDialogueNode
    var currentDialogueNode = rootDialogue
    var inputBound = 1

    fun displayDialogue () {
        println(currentDialogueNode.nodeDialogue)
    }
    fun displayOptions () {
        val dialogueIterator = currentDialogueNode.dialogueChoices.iterator()
        var i = 1
        while (dialogueIterator.hasNext()) {
            println(i.toString() + ") " + dialogueIterator.next())
            i++
        }
        inputBound = i
    }
    fun changeNode (nextNode : DialogueNode) {
        currentDialogueNode = nextNode
    }
    fun traverseDialogue (){
        displayDialogue()
        if (currentDialogueNode.childrenDialogueList.isEmpty()) {
            println("Game Over")
            return
        }
        else {
            displayOptions()
            val userChoice = Integer.valueOf(readLine())
            if (userChoice in 1..inputBound) {
                // println(currentDialogueNode.childrenDialogueList[userChoice-1].nodeDialogue)
                currentDialogueNode = currentDialogueNode.childrenDialogueList[userChoice - 1]
                traverseDialogue()
            } else {
                println("out of bounds, try another input")
                traverseDialogue()
            }
        }

    }

}
fun main(args: Array<String>) {
    //notation Branch n Node n Option n
    val initNode = DialogueNode("Hello adventurer!")
        val b1n1 = DialogueNode("How are you doing?")
        initNode.appendDialogue(b1n1,"hello!")

        val b1n2 = DialogueNode("Rude...")
        initNode.appendDialogue(b1n2,"...")


    val dialogue = Dialogue(initNode)

    dialogue.traverseDialogue()


}



