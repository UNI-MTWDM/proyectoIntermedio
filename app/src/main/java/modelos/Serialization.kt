package modelos

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class Serialization {
    companion object {
        fun serializeCharacter(character: Character): ByteArray {
            val byteArrayOutputStream = ByteArrayOutputStream()
            val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objectOutputStream.writeObject(character)
            objectOutputStream.close()

            return byteArrayOutputStream.toByteArray()
        }


        fun deserializeCharacter(byteArray: ByteArray): Character {
            val byteArrayInputStream = ByteArrayInputStream(byteArray)
            val objectInputStream = ObjectInputStream(byteArrayInputStream)

            val character = objectInputStream.readObject() as Character

            objectInputStream.close()

            return character
        }

        fun serializeArray(data: Array<Character>): ByteArray {
            val byteArrayOutputStream = ByteArrayOutputStream()
            val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objectOutputStream.writeObject(data)
            objectOutputStream.close()

            return byteArrayOutputStream.toByteArray()
        }

        fun deserializeArray(data: ByteArray): Array<Character> {
            val byteArrayInputStream = ByteArrayInputStream(data)
            val objectInputStream = ObjectInputStream(byteArrayInputStream)
            val info = objectInputStream.readObject() as Array<Character>
            objectInputStream.close()

            return info
        }

        fun serializeList(data: List<Character>): ByteArray {
            val byteArrayOutputStream = ByteArrayOutputStream()
            val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)

            // Convert the List to an array before serialization
            val dataArray = data.toTypedArray()
            objectOutputStream.writeObject(dataArray)

            objectOutputStream.close()

            return byteArrayOutputStream.toByteArray()
        }
        fun deserializeList(byteArray: ByteArray): List<Character> {
            val byteArrayInputStream = ByteArrayInputStream(byteArray)
            val objectInputStream = ObjectInputStream(byteArrayInputStream)

            // Read the array back and cast it to List<Character>
            val dataArray = objectInputStream.readObject() as Array<Character>

            objectInputStream.close()

            return dataArray.toList()
        }
    }
}