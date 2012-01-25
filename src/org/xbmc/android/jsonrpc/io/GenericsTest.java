package org.xbmc.android.jsonrpc.io;

import java.util.ArrayList;
import java.util.List;

public class GenericsTest {

	public static class A<T1> {

		public void helloB(B<T1> b) {

		}
	}

	public static class B<T2> {
	}
	
	public static class Tuple<T> {
		final A<T> a;
		final B<T> b;
		public Tuple(A<T>a , B<T> b) {
			this.a = a;
			this.b = b;
		}
	}

	public static class M {

		private final List<Tuple<?>> list = new ArrayList<Tuple<?>>();
		
		public <T> void save(A<T> a, B<T> b) {
			list.add(new Tuple<T>(a, b));
		}

		public void doSomething() {

			final A<String> a = new A<String>();
			final B<String> b = new B<String>();

			save(a, b);

			/* doesn't compile: The method helloB(Test.B<capture#1-of ?>) in the
			 * type Test.A<capture#1-of ?> is not applicable for the arguments
			 * (Test.B<capture#2-of ?>)
			 */
			final Tuple<?> item = list.get(0);
			item.a.helloB((B)item.b);
		}
	}
}
