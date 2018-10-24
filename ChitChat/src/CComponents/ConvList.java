package CComponents;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import javax.swing.JOptionPane;

import Exceptions.*;
import Constants.Constants;


public class ConvList<T> implements List<T> {

	List<T> convs;
	int model;

	
	@SuppressWarnings("unchecked")
	public ConvList(int modelChoose) throws ConvListModelChooseException {
		convs = (List<T>) new Vector<Convasation>();
		switch (modelChoose) {
		case Constants.CONVLIST_CONTACTS:
			model = Constants.CONVLIST_CONTACTS;
			break;
		case Constants.CONVLIST_MESSAGE:
			model = Constants.CONVLIST_MESSAGE;
			break;
		default:
			throw new ConvListModelChooseException();
		}
	}

	public T find(int ID)
	{
		for(T i : convs)
		{
			if(((Convasation)i).equals(ID))
				return i;
		}
		return null;
	}
	
	@Override
	public boolean add(T arg0) {
		if (convs.add(arg0))
			return true;
		return false;
	}

	@Override
	public void add(int arg0, T arg1) {
		convs.add(arg0, arg1);
	}

	@Override
	public boolean addAll(Collection<? extends T> arg0) {
		if (convs.addAll(arg0))
			return true;
		return false;
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends T> arg1) {
		if (convs.addAll(arg0, arg1))
			return true;
		return false;
	}

	@Override
	public void clear() {
		convs.clear();
	}

	@Override
	public boolean contains(Object arg0) {
		if (convs.contains(arg0))
			return true;
		return false;
	}
	public boolean contains(int ID) {
		if(this.find(ID)!=null)return true;
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		if (convs.containsAll(arg0))
			return true;
		return false;
	}

	@Override
	public T get(int ID) {
		return this.find(ID);
	}

	@Override
	public int indexOf(Object arg0) {
		return convs.indexOf(arg0);
	}
	public int indefOf(int ID) {
		return this.indexOf(this.find(ID));
	}

	@Override
	public boolean isEmpty() {
		if (convs.isEmpty())
			return true;
		return false;
	}

	@Override
	public Iterator<T> iterator() {

		return convs.iterator();
	}

	@Override
	public int lastIndexOf(Object arg0) {

		return convs.lastIndexOf(arg0);
	}

	@Override
	public ListIterator<T> listIterator() {

		return convs.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int arg0) {
		return convs.listIterator(arg0);
	}

	@Override
	public boolean remove(Object arg0) {
		if (convs.remove(arg0))
			return true;
		return false;
	}

	@Override
	public T remove(int ID) {
		return convs.remove(this.indefOf(ID));
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		if (convs.removeAll(arg0))
			return true;
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		if (convs.retainAll(arg0))
			return true;
		return false;
	}

	@Override
	public T set(int arg0, T arg1) {
		return convs.set(arg0, arg1);
	}

	@Override
	public int size() {
		return convs.size();
	}

	@Override
	public List<T> subList(int arg0, int arg1) {
		return convs.subList(arg0, arg1);
	}

	@Override
	public Object[] toArray() {
		return convs.toArray();

	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		return convs.toArray(arg0);
	}

	public void sort() {
		Collections.sort(convs, new Comparator<T>() {
			@Override
			public int compare(Object e1, Object e2) {
				Convasation arg0 = (Convasation) e1;
				Convasation arg1 = (Convasation) e2;
				switch (model) {
				case Constants.CONVLIST_CONTACTS:
					return arg1.onlineState - arg0.onlineState;//在线在前(online = 1)
				case Constants.CONVLIST_MESSAGE:
					return arg0.isRead - arg1.isRead;//未读在前(read = 1)
				default:
					try {
						throw new ConvListCompareException();
					} catch (ConvListCompareException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),"",JOptionPane.PLAIN_MESSAGE);
						return 0;
					}//catch
				}//switch
			}//Comparator<T>
		});//Collections.sort()
	}//sort()
}
