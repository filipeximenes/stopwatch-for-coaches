package base.stopwatch.util;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;



public abstract class AbstractArrayAdapter<T> extends ArrayAdapter<T> {
	private Activity act;
	private ArrayList<T> arraylist;
	private AbsListView listView;
	private LinearLayout blockView;
	
	/*
	 * getListViewId - should return the reference to the AbsListView
	 * getBlockViewId - should return the reference to the include tag that 
	 * 					includes the xml file containing the AbsListView
	 *  fillListView - lookup at the database the items to be added on the AbsListView
	 */
	protected abstract int getListViewId();
	protected abstract int getBlockViewId();
	protected abstract void fillListView();
	
	public AbstractArrayAdapter(Activity act, int ViewResourceId, ArrayList<T> objects) {
		super(act, ViewResourceId, objects);
		this.arraylist = objects;
		this.act = act;
		
		setViews();
		
		setListViewClickLister();
		setListViewOnItemLongClickListener();
		setListViewTouchLister();
	}
	
	/*
	 * To add a touch listener.
	 */
	protected boolean listViewOnTouchListener(View v, MotionEvent event){
		return false;	
	}
	
	/*
	 * To add a item click listener to the AbsListView.
	 */
	protected void listViewItemOnClickListener(AdapterView<?> adapterView, View view, int position, long id){
		
	}
	
	protected void listViewItemOnLongClickListener(AdapterView<?> adapterView, View view, int position, long id){
		
	}
	
	protected final void setListViewTouchLister(){
		getListView().setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return listViewOnTouchListener(v, event);
			}
		});
	}
	
	protected final void setListViewClickLister(){
		getListView().setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				listViewItemOnClickListener(adapterView, view, position, id);
			}
		});
	}
	
	protected final void setListViewOnItemLongClickListener(){
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
				listViewItemOnLongClickListener(adapterView, view, position, id);
				return true;
			}
		});
	}
	
	public ArrayList<T> getArrayList(){
		return arraylist;
	}
	
	public AbsListView getListView(){
		return listView;
	}
	
	public LinearLayout getBlockView(){
		return blockView;
	}
	
	private final void setViews(){
		blockView = (LinearLayout) act.findViewById(getBlockViewId());
		listView = (AbsListView) blockView.findViewById(getListViewId());
		listView.setAdapter(this);		
	}
	
	public void addAll(Collection<? extends T> collection) {
		for (T item: collection){
			add(item);
		}
	}
	
	public void setBlockViewVisible(){
		blockView.setVisibility(LinearLayout.VISIBLE);
	}
	
	public void setBlockViewInvisible(){
		blockView.setVisibility(LinearLayout.INVISIBLE);
	}
	
	public void setBlockViewGone(){
		blockView.setVisibility(LinearLayout.GONE);
	}
	
	public boolean contains(T a){
		return arraylist.contains(a);
	}
}
