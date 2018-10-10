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
import Client.Contacts;
import Client.MainWindow;
import Client.Message;
import Constants.Constants;
import CComponents.Conts;

public class ConvList<T> implements List<T> {

	List<T> convs;
	int model;

	@SuppressWarnings("unchecked")
	public ConvList(int modelChoose) throws ConvListModelChooseException {
		convs = (List<T>) new Vector<Conts>();
		switch (modelChoose) {
		case Constants.CONTACTS:
			model = Constants.CONTACTS;
			break;
		case Constants.MESSAGE:
			model = Constants.MESSAGE;
			break;
		default:
			throw new ConvListModelChooseException();
		}
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

	@Override
	public boolean containsAll(Collection<?> arg0) {
		if (convs.containsAll(arg0))
			return true;
		return false;
	}

	@Override
	public T get(int arg0) {
		return convs.get(arg0);
	}

	@Override
	public int indexOf(Object arg0) {
		return convs.indexOf(arg0);
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
	public T remove(int arg0) {
		return convs.remove(arg0);
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
				Conts arg0 = (Conts) e1;
				Conts arg1 = (Conts) e2;
				switch (model) {
				case Constants.CONTACTS:
					return arg0.onlineState - arg1.onlineState;
				case Constants.MESSAGE:
					return arg0.isRead - arg1.isRead;
				default:
					try {
						throw new ConvListCompareException();
					} catch (ConvListCompareException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),"",JOptionPane.PLAIN_MESSAGE);
						return 0;
					}
				}
			}
		});
	}
}
