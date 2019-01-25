	.section	__TEXT,__text,regular,pure_instructions
	.globl	_f
	.align	4, 0x90
_f:                                     ## @f
	.cfi_startproc
## BB#0:
	pushq	%rbp
Ltmp2:
	.cfi_def_cfa_offset 16
Ltmp3:
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
Ltmp4:
	.cfi_def_cfa_register %rbp
	leal	41(%rdi), %eax
	popq	%rbp
	ret
	.cfi_endproc

	.globl	_g
	.align	4, 0x90
_g:                                     ## @g
	.cfi_startproc
## BB#0:
	pushq	%rbp
Ltmp7:
	.cfi_def_cfa_offset 16
Ltmp8:
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
Ltmp9:
	.cfi_def_cfa_register %rbp
	leal	(%rdi,%rdi,4), %eax
	imull	$7, %esi, %edi
	addl	%eax, %edi
	callq	_f
	addl	$-30, %eax
	popq	%rbp
	ret
	.cfi_endproc

	.globl	_main
	.align	4, 0x90
_main:                                  ## @main
	.cfi_startproc
## BB#0:
	pushq	%rbp
Ltmp12:
	.cfi_def_cfa_offset 16
Ltmp13:
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
Ltmp14:
	.cfi_def_cfa_register %rbp
	movl	$2, %edi
	movl	$3, %esi
	popq	%rbp
	jmp	_g                      ## TAILCALL
	.cfi_endproc

	.section	__TEXT,__const
	.globl	_coefficient            ## @coefficient
	.align	2
_coefficient:
	.long	30                      ## 0x1e


.subsections_via_symbols
