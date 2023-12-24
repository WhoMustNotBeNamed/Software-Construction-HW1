package ru.hse.software.construction.view

class ConsoleView {
    // ANSI Escape коды для цветов
    private val ANSI_RESET = "\u001B[0m"
    private val ANSI_BLUE = "\u001B[34m"
        fun displayScheduleMenu() {
            println("$ANSI_BLUE\nМеню взаимодействия: $ANSI_RESET")
            println("1. Добавить фильм")
            println("2. Удалить фильм")
            println("3. Открыть фильм")
            println("0. Выйти")
        }

        fun displayMovieMenu() {
            println("$ANSI_BLUE Меню взаимодействия: $ANSI_RESET")
            println("1. Обновить информацию о фильме")
            println("2. Купить билет")
            println("3. Вернуть билет")
            println("4. Отметить занятое место")
            println("0. Выйти")
        }
}