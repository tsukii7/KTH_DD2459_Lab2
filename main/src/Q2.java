//Write	appropriate	pre	and	postconditions	using	the	JML	language	(ie.	write	appropriate
//requires-ensures conditions)	for:
//        (i)	sorting,
//        (ii)	searching,	hint	assume	key is	a	native	data	type	e.g.	int key (otherwise	must	check
//        key is	also	non-null)
//        (iii)	membership,
//        (iv)	binary	searching

/*@ requires a != null;
  @ ensures (\forall int i; 0 <= i && i < a.length - 1; a[i] <= a[i + 1];
  @ ensures \forall int value;
  @     (\num_of int i; 0 <= i < a.length; a[i] == value) ==
  @     (\num_of int i; 0 <= i < \old(a.length); \old(a[i]) == value);
  @*/
// (i) Sorting

/*@ requires a != null;
  @ ensures \result >= 0 ==> \result < a.length && a[\result] == key;
  @ ensures \result == -1 ==> \forall int i; 0 <= i < a.length; a[i] != key;
  @ ensures \result >= -1 && \result < a.length;
  @ ensures \forall int i; 0 <= i < a.length; a[i] == \old(a[i]);
  @*/
// (ii) Searching

/*@ requires a != null;
  @ ensures \result == true <==> (\exists int i; 0 <= i < a.length; a[i] == key);
  @ ensures \result == false <==> (\forall int i; 0 <= i < a.length; a[i] != key);
  @ ensures \forall int i; 0 <= i < a.length; a[i] == \old(a[i]);
  @*/
// (iii) Membership

/*@ requires a != null && (\forall int i; 0 <= i && i < a.length - 1; a[i] <= a[i + 1]);
  @ ensures \result >= 0 ==> \result < a.length && a[\result] == key;
  @ ensures \result == -1 ==> \forall int i; 0 <= i && i < a.length; a[i] != key;
  @ ensures \result >= -1 && \result < a.length;
  @ ensures \forall int i; 0 <= i < a.length; a[i] == \old(a[i]);
  @*/
// (iv) Binary	searching
