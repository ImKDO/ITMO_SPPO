section .text
    char_buffre: db 0

; ASCII
%define SPACE_SYM   0x20
%define TAB_SYM     0x9
%define NEW_LINE 0xA
%define NULL_SYM    0x0

; Системные вызовы
%define SYS_READ  0
%define SYS_WRITE 1
%define SYS_EXIT  60

; Коды дескриптора
%define STDIN     0
%define STDOUT    1


; Принимает код возврата и завершает текущий процесс
exit: 
    mov rax, SYS_EXIT
    syscall
; Принимает указатель на нуль-терминированную строку, возвращает её длину
string_length:
    xor rax, rax            ;Подготовка счетчика
.loop:
    cmp byte [rdi + rax], NULL_SYM       ;Попали ли мы на нуль-терминированный символ
    jz .done                 ;Если да, то посчитали всю длину
    inc rax                 ;Счетчик количества символов в строке
    jmp .loop                ;Цикл продолжается
.done: 
    ret

; Принимает указатель на нуль-терминированную строку, выводит её в stdout
print_string:
    push rdi                ;Сохраняем строку
    call string_length
    mov rdx, rax            ;Длина строки
    pop rsi                 ;Кладем ее в rsi
    mov rax, SYS_WRITE      ;Выводим на экран
    mov rdi, STDOUT
    syscall
    ret

; Переводит строку (выводит символ с кодом 0xA)
print_newline:
    mov rsi, NEW_LINE


; Принимает код символа и выводит его в stdout
print_char:
    sub rsp, 8
    push rdi
    mov rsi, rsp
    mov rax, SYS_WRITE
    mov rdi, STDOUT
    mov rdx, 1
    syscall
    pop rdi
    add rsp, 8
    ret


; Выводит беззнаковое 8-байтовое число в десятичном формате 
; Совет: выделите место в стеке и храните там результаты деления
; Не забудьте перевести цифры в их ASCII коды.
; Выводит знаковое 8-байтовое число в десятичном формате 

print_int:
    cmp rdi, NULL_SYM
    jns print_uint

    neg rdi
    push rdi
    mov rdi, "-"
    call print_char
    pop rdi
    jmp print_uint 



    
print_uint:
push rbx
push r11

mov rax, rdi                ;Готовим rax для деления
mov rbx, 10                 ;Готовим делитель 10, для запоминания последней цифры 
mov r11, rsp                ;Закидываем ссылку стека на r11, чтобы быстрее с данными работать
sub rsp, 24                 ;Расширяем стек для заполнения
dec r11                     ;Чтобы добавить нуль-терминатор
mov byte [r11], NULL_SYM

    .loop:
        xor rdx, rdx        ;Очищаем для запоминания последней цифры
        div rbx             ;Запоминаем в rdx последнюю цифру
        add dl, "0"         ;Преобразовываем в ASCII для показа в stdout
        dec r11             ;Двигаем стек в регистре
        mov byte [r11], dl  ;Заполняем число
        cmp rax, NULL_SYM          ;Проверка на конец строки
        jnz .loop
        
    mov rdi, r11            ;Закидываем в rdi число, чтобы через print_string вывести результат
    call print_string
    add rsp, 24             ;Выравниваем стек
    pop r11
    pop rbx
    ret





; Принимает два указателя на нуль-терминированные строки, возвращает 1 если они равны, 0 иначе
string_equals:

.compare:
    lodsb
    scasb
    jnz .not_equal
    test al, al
    jz .equal
    jmp .compare

.not_equal:
    xor rax, rax         ; Возвращаем 0, если строки не равны
    ret 

.equal:
        mov rax, 1       ; Возвращаем 1, если строки равны
        ret



; Читает один символ из stdin и возвращает его. Возвращает 0 если достигнут конец потока
read_char:
    push 0              ;Здесь запишется наш введенный символ
    mov rsi, rsp        
    mov rax, SYS_READ
    mov rdi, STDIN
    mov rdx, 1
    syscall
    pop rax             ;Кладем символ
    ret

; Принимает: адрес начала буфера, размер буфера
; Читает в буфер слово из stdin, пропуская пробельные символы в начале, .
; Пробельные символы это пробел 0x20, табуляция 0x9 и перевод строки 0xA.
; Останавливается и возвращает 0 если слово слишком большое для буфера
; При успехе возвращает адрес буфера в rax, длину слова в rdx.
; При неудаче возвращает 0 в rax
; Эта функция должна дописывать к слову нуль-терминатор

read_word:
    xor rdx, rdx                ; Обнуляем длину слова
    push rbx                    ; Сохраняем регистры
    push r12
    push r13

    mov rbx, rdi                ; rbx хранит адрес буфера
    mov r12, rsi                ; r12 хранит размер буфера
    xor r13, r13                ; r13 будет хранить длину слова

    .parse_space_symbs:         ; Пропускаем пробельные символы
    call read_char
    cmp al, NULL_SYM
    je .too_long                ; Конец файла
    cmp al, SPACE_SYM           ; Пробел
    je .parse_space_symbs
    cmp al, NEW_LINE            ; Перевод строки
    je .parse_space_symbs
    cmp al, TAB_SYM             ; Табуляция
    je .parse_space_symbs

    .loop_read_word:
    cmp r13, r12                ; Проверяем, не переполнили ли буфер
    jae .too_long               ; Если переполнили, завершаем

    mov byte [rbx + r13], al    ; Записываем символ в буфер
    inc r13                     ; Увеличиваем длину слова

    call read_char              ; Читаем следующий символ
    cmp al, NULL_SYM
    je .end_read                ; Конец файла

    cmp al, SPACE_SYM           ; Пробел
    je .end_read
    cmp al, NEW_LINE            ; Перевод строки
    je .end_read
    cmp al, TAB_SYM             ; Табуляция
    je .end_read

    jmp .loop_read_word

    .too_long:                  ; Если слово слишком длинное
    xor rax, rax                ; Возвращаем 0
    xor rdx, rdx                ; Длина слова = 0
    jmp .cleanup

    .end_read:                  ; Завершаем чтение
    mov byte [rbx + r13], NULL_SYM     ; Добавляем нуль-терминатор
    mov rax, rbx                ; Возвращаем адрес буфера
    mov rdx, r13                ; Возвращаем длину слова

    .cleanup:
    pop r13                     ; Восстанавливаем регистры
    pop r12
    pop rbx
    ret
 

; Принимает указатель на строку, пытается
; прочитать из её начала беззнаковое число.
; Возвращает в rax: число, rdx : его длину в символах
; rdx = 0 если число прочитать не удалось
parse_uint:
  xor rax, rax
  xor r9, r9    ; Готовим счетчик
  sub rsp, 40
  xor r10, r10    ; Готовим буфер
  mov r11, 10     ; Готовим регистр, которое будет двигать число

  
    
  .parse_loop:
    mov r10b, byte [rdi+r9] ;получаем символ
    sub r10b, '0'                ; ASCII
    jl .done
    cmp r10b, 9                  ; Смотрим цифру
    jg .done

    mul r11                      ; rax *= 10

    add rax, r10                 ; rax = rax * 10 + r10

    inc r9                      ; Двигаем указатель
    jmp .parse_loop

  .done:
    mov rdx, r9
    add rsp, 40
    ret


; Принимает указатель на строку, пытается
; прочитать из её начала знаковое число.
; Если есть знак, пробелы между ним и числом не разрешены.
; Возвращает в rax: число, rdx : его длину в символах (включая знак, если он был) 
; rdx = 0 если число прочитать не удалось
parse_int:
    cmp byte [rdi], "-"         ; Проверяем на + и - в начале числа
    jz .parse_neg
    cmp byte [rdi], "+"
    jz .parse_positive

    jmp parse_uint              ; Если знака нет => число uint

     .parse_positive:
    push rdi                    ; Сохраняем состояние rdi, потому что при тестах rdi заполняется мусором при call
    inc rdi
    call parse_uint
    inc rdx
    pop rdi
    ret

    .parse_neg:
    push rdi                    ; Сохраняем состояние rdi, потому что при тестах rdi заполняется мусором при call
    inc rdi 
    call parse_uint
    inc rdx
    neg rax
    pop rdi
    ret

; Принимает указатель на строку, указатель на буфер и длину буфера
; Копирует строку в буфер
; Возвращает длину строки если она умещается в буфер, иначе 0
string_copy:
    push rdi
    push rsi
    push r11
    call string_length      ; Вычисляем длину строки
    
    cmp rdx, rax            ; Сравниваем длину буфера с длиной строки
    jbe .done_zero           ; Если строка больше буфера, выходим

    mov rcx, rax            ; Устанавливаем длину строки для копирования    
    pop r11
    pop rsi
    pop rdi

    mov r11, rdi                ; Сохраняем указатель на строку
    mov rdi, rsi                ; Указатель на буфер
    mov rsi, r11                ; Указатель на строку

    rep movsb                   ; Копируем строку в буфер

    mov byte [rdi], NULL_SYM    ; Добавляем нулевой байт в конец строки

    ret

    .done_zero:
    pop r11
    pop rsi
    pop rdi
    xor rax, rax            ; Возвращаем 0
    ret

