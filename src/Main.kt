fun main()
{
    while (true) {
        println("""
            Выберите задание:
            1. Подсчёт различных цифр в массиве
            2. Проверка симметричности матрицы
            3. Шифровка/дешифровка текста
            4. Пересечение массивов с учётом повторений
            5. Группировка слов по буквам
            0. Выход
        """.trim())
        print("Ваш выбор: ")
        println()

        val task = readln().trim()

        if (task == "0") {
            println("Завершение программы!")
            break
        }

        val exercise = task.toIntOrNull()
        when (exercise) {
            1 -> task1()
            2 -> task2()
            3 -> task3()
            4 -> task4()
            5 -> task5()
            else -> println("Неверный выбор. Попробуйте снова.")
        }
        println()
    }
}

fun task1()
{
    println("Эта программа подсчитывает количество различных цифр в массиве и выводит количество различных цифр, используемых в данном массиве")
    println("-----------------------------------------------------------------------------------------------------------------------------------")
    print("Введите количество столбцов: ")
    val column = readln().trim().toInt()
    print("Введите количество строк: ")
    val row = readln().trim().toInt()

    if (column <= 0 || row <= 0)
    {
        print("Количество строк и столбцов должно быть больше 0!")
        return
    }
    val matrix = Array(row) { Array(column) { (100..255).random() } }

    println("\nСформированная матрица:")
    for (i in matrix.indices)
    {
        for (j in matrix[i].indices)
        {
            print("${matrix[i][j]}".padStart(4))
        }
        println()
    }

    val unique = mutableSetOf<Char>()
    for (i in matrix.indices)
    {
        for (j in matrix[i].indices)
        {

            val numbs = matrix[i][j].toString()
            unique.addAll(numbs.toSet())
        }
    }

    println("\nВ массиве использовано ${unique.size} различных цифр: ${unique.sorted().joinToString(", ")}")
}

fun task2()
{
    println("Эта программа выясняет, симметрична ли матрица относительно главной диагонали.")
    println("-----------------------------------------------------------------------------------------------------------------------------------")

    val matrix2 = arrayOf(
        intArrayOf(5, 9, 6, 7, 2),
        intArrayOf(9, 8, 4, 5, 3),
        intArrayOf(6, 4, 3, 8, 7),
        intArrayOf(7, 5, 8, 4, 8),
        intArrayOf(2, 3, 7, 8, 1)
    )

    for (row in matrix2)
    {
        for (value in row)
        {
            print("${value}".padStart(4))
        }
        println()
    }

    val n = matrix2.size
    var symmetry = true
    for (i in 0 until n)
    {
        for (j in 0 until n)
        {
            if (matrix2[i][j] != matrix2[j][i])
            {
                symmetry = false
                break
            }
        }
        if (!symmetry) break
    }

    if (symmetry)
    {
        println("Массив симметричен относительно главной диагонали")
    }
    else
    {
        println("Матрица НЕ симметрична")
    }
}

fun task3()
{
    println("Данная программа способна шифровать и дешифровать строковое выражение заданное пользователем")
    println("-----------------------------------------------------------------------------------------------------------------------------------")
    val alphabet = charArrayOf(
        'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И',
        'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т',
        'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ь', 'Ы', 'Ъ',
        'Э', 'Ю', 'Я'
    )

    val position = intArrayOf(
        21, 13, 4, 20, 22, 1, 25, 12, 24, 14,
        2, 28, 9, 23, 3, 29, 6, 16, 15, 11,
        26, 5, 30, 27, 8, 18, 10, 33, 31, 32,
        19, 7, 17
    )

    val charToNumber = mutableMapOf<Char, Int>()
    for (i in alphabet.indices)
    {
        charToNumber[alphabet[i]] = position[i]
    }

    val numberToChar = mutableMapOf<Int, Char>()
    for ((char, num) in charToNumber)
    {
        numberToChar[num] = char
    }

    fun encrypt(text: String, key: String, charToNumber: Map<Char, Int>, numberToChar: Map<Int, Char>): String
    {
        val result = StringBuilder()
        for ((i, c) in text.withIndex())
        {
            val keyChar = key[i % key.length]
            val shift = charToNumber[keyChar]!!
            val originalNumber = charToNumber[c]!!
            val newNumber = (originalNumber + shift - 1) % 33 + 1
            result.append(numberToChar[newNumber])
        }
        return result.toString()
    }

    fun decrypt(text: String, key: String, charToNumber: Map<Char, Int>, numberToChar: Map<Int, Char>): String
    {
        val result = StringBuilder()
        for ((i, c) in text.withIndex())
        {
            val keyChar = key[i % key.length]
            val shift = charToNumber[keyChar]!!
            val encryptedNumber = charToNumber[c]!!
            val newNumber = (encryptedNumber - shift - 1 + 33) % 33 + 1
            result.append(numberToChar[newNumber])
        }
        return result.toString()
    }

    println("Введите 1 для шифрования и 2 для дешифровки: ")
    val mode = readLine()!!.toIntOrNull()

    println("Введите ключевое слово (только русский алфавит): ")
    val key = readLine()!!.uppercase()

    println("Введите текст (только русский алфавит): ")
    val text = readLine()!!.uppercase()

    val result = if (mode == 1) {
        encrypt(text, key, charToNumber, numberToChar)
    } else if (mode == 2) {
        decrypt(text, key, charToNumber, numberToChar)
    } else {
        "Указан неверный режим"
    }

    println("Результат: $result")
}

fun task4()
{
    println("Эта программа находит пересечение двух массивов с учётом количества повторений.")

    print("Введите первый массив (числа через пробел): ")
    val input1 = readLine()?.trim() ?: ""
    if (input1.isEmpty())
    {
        println("Первый массив не может быть пустым!")
        return
    }

    print("Введите второй массив (числа через пробел): ")
    val input2 = readLine()?.trim() ?: ""
    if (input2.isEmpty())
    {
        println("Второй массив не может быть пустым!")
        return
    }

    val array1 = try
    {
        input1.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toIntArray()
    } catch (e: NumberFormatException)
    {
        println("Ошибка: в первом массиве есть нечисловые значения!")
        return
    }

    val array2 = try {
        input2.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toIntArray()
    } catch (e: NumberFormatException)
    {
        println("Ошибка: во втором массиве есть нечисловые значения!")
        return
    }

    val repeat1 = mutableMapOf<Int, Int>()
    for (num in array1)
    {
        repeat1[num] = repeat1.getOrDefault(num, 0) + 1
    }

    val repeat2 = mutableMapOf<Int, Int>()
    for (num in array2)
    {
        repeat2[num] = repeat2.getOrDefault(num, 0) + 1
    }

    val result = mutableListOf<Int>()

    for ((num, count1) in repeat1)
    {
        if (repeat2.containsKey(num))
        {
            val count2 = repeat2[num]!!
            val minCount = minOf(count1, count2)
            repeat(minCount)
            {
                result.add(num)
            }
        }
    }

    result.sort()
    println("Пересечение массивов: $result")
}
fun task5() {
    println("Эта программа группирует слова по признаку «состоят из одинаковых букв»")
    println("-----------------------------------------------------------------------------------------------------------------------------------")
    println("Введите слова через пробел: ")
    val list = readln().trim().split(" ").map { it.toString() }.toMutableList()

    val groups = list.groupBy { list -> list.lowercase().toCharArray().sorted().joinToString ("") }
    println("Результат:")
    for ((key, group) in groups)
    {
        println("${group.joinToString(", ")}")

    }

    println("\n\nдля продолжениея нажмите Enter")
    readln()
}