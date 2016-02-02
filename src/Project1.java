import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;


public class Project1 {
	static int first=0,last=0;
	static int[] b;
	static int[] e;//bit array
	static int[] c;//sorted array of a
	static int[] a; //original array
	static int[] r;//auxillary array for index
	static int[] t,temp;
	static int [] tall;
	static int textlen = 0;
	static int ver=0;
	static int pat=0;
	public Project1() {
		// TODO Auto-generated constructor stub


	}

	public static void main(String[] args) {
		//int key = 0;
		// TODO Auto-generated method stub
		//java.util.Arrays.binarySearch(b, key);//gives the index of the array
		long startTime = new Date().getTime();
		int x=(int)(100*Math.random());
		int g=0;
		a=new int[10];
		b=new int[10];
		e=new int[10];
		c=new int[10];
		r=new int[10];
		tall = new int [7000];
		readtext();
		temp=new int[textlen];
		for(int i=x;i<x+10;i++){
			a[g]=tall[i];
			g++;
		}
		System.arraycopy(a, 0, c, 0, a.length);
		bitgen(a,a.length);
		if(tall[x+9]<tall[x+10])
		{
			b[9]=1;
		}
		else
		{
			b[9]=0;
		}
		sort(c);
		pre(c);

			modbitgen(tall, textlen);

			searchSubString(temp, b);
			System.out.println("Number of Verified Matches:"+ ver);
			System.out.println("Number of Matches:"+pat);
			long endTime = new Date().getTime();
			System.out.println("elapsed milliseconds: " + (endTime - startTime));


	}

	private static void readtext() {
		// TODO Auto-generated method stub
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("tall.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		while(scanner.hasNextInt())
		{
		     tall[textlen++] = scanner.nextInt();
		}


	}

	private static void pre(int[] x) {
		// TODO Auto-generated method stub
		for(int i=0;i<x.length;i++)
		{
			 if(i==x.length-1){
				 r[i]=find(a,x[i]);
				 e[i]=0;
			 }
			 else
			 {
				 if(x[i]==x[i+1])
				 {
					 r[i]=find(a,x[i]);
					 r[i+1]=revfind(a,x[i+1]);
					 e[i]=1;
					 i++;
					 if(i==x.length-1)
					 {
						 e[i]=0;
						 i++;
					 }

				 }
				 else
				 {
					 r[i]=find(a,x[i]);
					 e[i]=0;
				 }

			 }
			//System.err.println(r[i]);

		}
	}

	private static int revfind(int[] x, int y) {
		// TODO Auto-generated method stub
		for(int i=x.length-1;i>=0;i--)
	    {
	      if (x[i] == y)     /* Searching element is present */
	      {

	          return i;
	      }
	   }
		return -1;
	}

	private static int find(int[] x, int y) {
		// TODO Auto-generated method stub
		for(int i=0;i<x.length;i++)
	    {
	      if (x[i] == y)     /* Searching element is present */
	      {

	          return i;
	      }
	   }
		return -1;
	}

	private static void bitgen(int[] x,int y){
		for(int i=0;i<y-1;i++)
		{
			if(x[i]<x[i+1])
			{
				b[i]=1;
			}
			else
			{
				b[i]=0;
			}

		}

	}

	private static void modbitgen(int[] x,int y){

		for(int i=0;i<y-1;i++)
		{
			if(x[i]<x[i+1])
			{
				temp[i]=1;
			}
			else
			{
				temp[i]=0;
			}
			temp[y-1]=1;
		}
	}

	public static void sort( int[] a)//radix sort but need to copy the pattern first
    {
        int i, m = a[0], exp = 1, n = a.length;
        int[] b = new int[10];
        for (i = 1; i < n; i++)
            if (a[i] > m)
                m = a[i];
        while (m / exp > 0)
        {
            int[] bucket = new int[10];

            for (i = 0; i < n; i++)
                bucket[(a[i] / exp) % 10]++;
            for (i = 1; i < 10; i++)
                bucket[i] += bucket[i - 1];
            for (i = n - 1; i >= 0; i--)
                b[--bucket[(a[i] / exp) % 10]] = a[i];
            for (i = 0; i < n; i++)
                a[i] = b[i];
            exp *= 10;
        }
    }
	/* First sort the pattern then find the index of each element in increasing order from the original pattern and store in array r
	 * After storing in array r generate array E by comparing for equalities.
	 */
	public static int[] preProcessPattern(int[] ptrn) {
        int i = 0, j = -1;
        int ptrnLen = ptrn.length;
        int[] b = new int[ptrnLen + 1];

        b[i] = j;
        while (i < ptrnLen) {
            while (j >= 0 && ptrn[i] != ptrn[j]) {
                // if there is mismatch consider next widest border
                j = b[j];
            }
            i++;
            j++;
            b[i] = j;
        }
        // print pettern, partial match table and index


        return b;
    }


    public static void searchSubString(int[] text, int[] ptrn) {
        int i = 0, j = 0;

        int ptrnLen = ptrn.length;
        int txtLen = text.length;

        int[] b = preProcessPattern(ptrn);

        while (i < txtLen) {
            while (j >= 0 && text[i] != ptrn[j]) {

                j = b[j];
            }
            i++;
            j++;

            // a match is found
            if (j == ptrnLen) {

                pat++;
                if(Verify(i-ptrnLen,ptrnLen)==1){
                	ver++;
                }

                j = b[j];
            }
        }
    }

	private static int Verify(int x, int y) {
		// TODO Auto-generated method stub
		for(int i=0;i<y-1;i++)
		{
			if(tall[x+r[i]]>tall[x+r[i+1]])
			{
				return 0;
			}
			else if((tall[x+r[i]]==tall[x+r[i+1]])&&e[i]==0)
			{
				return 0;
			}
			else if((tall[x+r[i]]<tall[x+r[i+1]])&&e[i]==1)
			{
				return 0;
			}
		}
		return 1;
	}


}
