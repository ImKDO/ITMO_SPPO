.PHONY: clean
 

t.o: t.asm
	nasm -f elf64 -o t.o t.asm

ex.o: ex.asm t.o
	nasm -f elf64 -o ex.o ex.asm

program: ex.o t.o
	ld -o program t.o ex.o

clean:
	rm -f *.o program

