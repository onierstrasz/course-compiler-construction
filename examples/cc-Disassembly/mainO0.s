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
	movl	%edi, -4(%rbp)
	movl	-4(%rbp), %edi
	addl	$11, %edi
	movl	%edi, -8(%rbp)
	movl	-8(%rbp), %edi
	addl	$13, %edi
	movl	%edi, -12(%rbp)
	movl	-12(%rbp), %edi
	addl	$17, %edi
	movl	%edi, %eax
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
	subq	$16, %rsp
	movl	%edi, -4(%rbp)
	movl	%esi, -8(%rbp)
	imull	$5, -4(%rbp), %esi
	movl	%esi, -12(%rbp)
	imull	$7, -8(%rbp), %esi
	movl	%esi, -16(%rbp)
	movl	-12(%rbp), %esi
	addl	-16(%rbp), %esi
	movl	%esi, %edi
	callq	_f
	subl	$30, %eax
	addq	$16, %rsp
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
	subq	$16, %rsp
	movl	$2, %eax
	movl	$3, %ecx
	movl	$0, -4(%rbp)
	movl	%edi, -8(%rbp)
	movq	%rsi, -16(%rbp)
	movl	%eax, %edi
	movl	%ecx, %esi
	callq	_g
	addq	$16, %rsp
	popq	%rbp
	ret
	.cfi_endproc

	.section	__TEXT,__const
	.globl	_coefficient            ## @coefficient
	.align	2
_coefficient:
	.long	30                      ## 0x1e


.subsections_via_symbols
