  global _start
  section .data
  messageErr: db "hello, stderr", 10
  messageOut: db "Hello, stdout", 10
  section .text
_start:
  mov rax, 1          ;syscall "write" 
  mov rdi, 1          ;descript stdout
  mov rsi, messageOut ;address messageOut
  mov rdx, 14         ;lenght messageOut
  syscall

 mov rax, 1           ;syscall "write"
  mov rdi, 2          ;descript stderr
  mov rsi, messageErr ;address messageErr
  mov rdx, 14         ;lenght messageErr
  syscall

  mov rax, 60         ;exit code
  xor rdi, rdi         ;return sucsesfull code
  syscall
