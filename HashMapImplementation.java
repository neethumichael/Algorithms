import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

class MyHashMap<K,V>
{
	private int elemCount =0;
	private Entry[] table = (Entry[]) Array.newInstance(Entry.class, 1024); 

	// Entry stores the Key and Value.It also stores next pointer to next value in
	// the linked list and stores the hash value computed while inserting.
	// Functions getKey(),getValue(),setKey(),setValue(),setNext() are used to
	// get the key,value and set key,value and next respectively.
	public class Entry
	{
		K key;
		V value;
		Entry next;
		int hash;

		public Entry(K key, V val) {
			this.key = key;
			this.value = val;
		}

		public K getKey() {
			return this.key;
		}

		public V getValue() {
			return this.value;
		}

		public void setKey(K Key) {
			this.key = Key;
		}

		public void setValue(V value) {
			this.value = value;
		}

		public void setNext(Entry next) {
			this.next = next;
		}
	}

	// Hash function. Hash value is computed using djb2 hash method.
	// 33 was chosen because:
    // multiplication is easy to compute using shift and add.
    // Also using 33 makes two copies of most of the input bits in the hash accumulator, and then spreads those bits relatively far apart.
	static int hash(String s)
	{
		int hash = 5381;
		for(int i=0;i<s.length();i++)
		{
			hash = 33 * hash ^ s.charAt(i);
		}
		return hash;	   
	}

	// returns size of the hashmap
	public int size(){
		return elemCount;
	}

	// returns the list of all Entry objects. ie all entries in the hash map
	public ArrayList<Entry> entrySet(){

		ArrayList<Entry> set = new ArrayList<Entry>();
		for(Entry p : table)
			if(p != null)
			{ 
				if(!set.contains(p))
					set.add(p);
				while(p.next!=null)
				{
					if(!set.contains(p))
						set.add(p.next);
					p = p.next;
				}
			}
		return set;
	}

	// returns the list of all keys
	public ArrayList<K> getAllKeys()
	{
		ArrayList<K> list = new ArrayList<K>();
		for(Entry p : table)
			if(p != null)
			{         
				if(!list.contains(p.key)) {
					list.add(p.key);
				}

				while(p.next!=null)
				{
					if(!list.contains(p.key))
						list.add(p.key);
					p = p.next;
				}
			}
		return list;
	}

	// insert the given key and value into the linked list.
	// computes hash to find the index, if the key is not present
	// key is added with value 1,
	// if key is present, key's value is incremented
	public void insert(K key, V value)
	{		
	key = (K)key.toString().toLowerCase();
	int index = hash(key.toString())& (table.length-1);

	if (table[index] == null) 
	{
		Entry e = new Entry(key,value);
		e.hash = hash(key.toString());
		table[index] = e;
		elemCount++;
		return;
	}
	else
	{
		Entry cur = table[index];

		while(true)
		{
			if (cur.getKey().equals(key))
			{			
				cur.setValue(value);
				return;
			}
			if (cur.next==null) break;
			cur = cur.next;
		}
		Entry e = new Entry(key,value);
		e.hash = hash(key.toString());
		cur.setNext(e);

		elemCount++;
		return;
	}
	}

	// Increment key takes a key as input, if the key is present
	// increment value by one, else add new entry as (key,1)
	public void increment(K key)
	{		
		key = (K)key.toString().toLowerCase();
		int index = hash(key.toString())& (table.length-1);

		if (table[index] == null) 
		{
			Integer a = 1;
			V value = (V)a;
			Entry e = new Entry(key,value);
			e.hash = hash(key.toString());
			table[index] = e;
			elemCount++;
			return;
		}
		else
		{
			Entry cur = table[index];

			while(true)
			{
				if (cur.getKey().equals(key))
				{		
					Integer value = (Integer)cur.getValue();
					value = value+1;
					cur.setValue((V)value);
					return;
				}
				if (cur.next==null) break;
				cur = cur.next;
			}
			Integer a = 1;
			V value = (V)a;
			Entry e = new Entry(key,value);
			e.hash = hash(key.toString());
			cur.setNext(e);
			elemCount++;
			return;
		}
	}

	// searches for the given key, if found return the key value
	public V find(K key)
	{

		if (key == null)
			return null;
		key = (K)key.toString().toLowerCase();
		int index = hash(key.toString())& (table.length-1);
		if (table[index] == null) return null;
		else
		{
			Entry cur = table[index];
			while(true)
			{
				if (cur.getKey().equals(key))
				{
					return cur.getValue();
				}
				if (cur.next==null) break;
				cur = cur.next;
			}
			return null;
		}
	}

	// delete the given key 
	public void delete(K key)
	{
		key = (K)key.toString().toLowerCase();
		int index = hash(key.toString())& (table.length-1);
		if (!(table[index] == null)) 
		{
			Entry cur = table[index];
			Entry prev = null;
			while(true)
			{
				if (cur.getKey().equals(key))
				{
					if(prev!=null)
					{
					prev.setNext(cur.next);
					}
					else
					{
						table[index] = cur.next;
					}
					elemCount--;
					break;			
				}
				if (cur.next == null) break;
				prev = cur;
				cur = cur.next;
			}
		}
	}

	// util function to check the utilization of hash map table.
	public int tableUtil()
	{
		int count = 0;
		for(int i=0;i<table.length;i++)
		{
			if(table[i]!= null)
				count++;
		}
		return count;
	}
}

class HashString
{
	public static  MyHashMap<String,Integer> doc_words = new MyHashMap<String,Integer>();
	public static void main(String args[])
	{
		try{
			HashString2 obj = new HashString2();


			while(true)
			{
				System.out.println("**********************************************************");
				System.out.println("Enter the operation you want to perform on HashMap");
				System.out.println(" 1.Word count on file \n 2.Insert\n 3.Find \n 4.Delete\n 5.Increase \n 6.ListAllKeys\n 7.Exit");
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				int option = Integer.parseInt(br.readLine());
				switch (option)
				{
				case 1:
				{
					System.out.println("Enter the file you want to read");
					String fileName = br.readLine();
					obj.readFile(fileName);	
					obj.PrintValues();									
					break;
				}
				case 2:
				{
					System.out.println("Enter the string/key you want to insert");
					String s = (br.readLine());
					System.out.println("Enter value for the string");
					int n = Integer.parseInt(br.readLine());
					doc_words.insert(s, n);	
					break;       		
				}
				case 3:
				{
					System.out.println("Enter the string/key you want to find");
					String s = (br.readLine());
					if(doc_words.find(s)==null)
					{
						System.out.println("Element not present");
					}
					else
					{
					int n=doc_words.find(s);
					System.out.println("The string "+s+" has value "+n);
					}
					break;
				}
				case 4:
				{
					System.out.println("Enter the string/key you want to delete");
					String s = (br.readLine());
					doc_words.delete(s);
					break;
				}
				case 5:
				{
					System.out.println("Enter the key for value you want to increase");
					String s = (br.readLine());
					doc_words.increment(s);
					break;
				}
				case 6:
				{
					ArrayList<String> list2 = new ArrayList<String>();
					
					
					if(doc_words.getAllKeys().size()==0)
					{
						System.out.println("No keys are present");
						break;
					}
					list2 = (ArrayList<String>)doc_words.getAllKeys();
					System.out.println("The list of keys are");
					for(String x : list2) {
						System.out.println(x);
					}

					break;
				}
				case 7:
				{
					System.exit(0);
				}

				default:
				{
					System.out.println("Invalid option.Try again");
					break;
				}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	// reads the input file
	public  void readFile(String fileName)
	{
		{
			String[] a = new String[10000];
			try
			{
				FileReader fr = new FileReader(fileName);
				BufferedReader br = new BufferedReader(fr);
				String line;
				while ((line = br.readLine()) != null) 
				{
					a = line.split(" ");	
					for(int i=0;i<a.length;i++)
					{						
						String temp = a[i].replaceAll("[^A-Za-z0-9]", "");
						temp = temp.toLowerCase();
						if(doc_words.find(temp) == null)
						{
							doc_words.insert(temp, 1);		
						}
						else
						{
							int count = (int)doc_words.find(temp)+1;
							doc_words.insert(temp,count);
						}
					}
				}
			}
			catch(Exception e)
			{
				System.out.println("error"+e.getMessage());
			}
		}
	}

	// write the output to the file
	public void PrintValues() throws IOException
	{
		PrintWriter output = new PrintWriter(new FileWriter("wordCount.out"));
		int wordcount = 0;
		String word = "word";
		String count = "count";
		output.format("%-20s|%-15s",word,count);
		output.print(System.getProperty("line.separator"));
		ArrayList<MyHashMap<String, Integer>.Entry> list =doc_words.entrySet();
		for(MyHashMap.Entry e : list )
		{

			output.format("%-20s|%-15s",e.getKey(),e.getValue());
			output.print(System.getProperty("line.separator"));
			wordcount = wordcount+(int)e.getValue();
		}
		System.out.println("Word count = "+wordcount);
		output.flush();
		output.close();
	}
}