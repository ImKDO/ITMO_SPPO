global _start1

extern exit
extern print_hex
global _start

section .text
  _start:
    call print_hex
    call exit
