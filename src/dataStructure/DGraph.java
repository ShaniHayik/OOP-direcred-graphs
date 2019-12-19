package dataStructure;

import utils.Point3D;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DGraph implements graph{
	private HashMap<Integer,node_data> Nodemap= new HashMap<>();
	private HashMap<Integer,HashMap<Integer,edge_data>> Edgemap= new HashMap<>();
	private int edgeSize;
	private int nodeSize;
	private int MC;

	public DGraph(){
		this.Nodemap= new HashMap<>();
		this.Edgemap = new HashMap<>();
		this.edgeSize=0;
		this.nodeSize=0;
		this.MC=0;
	}

	@Override
	public node_data getNode(int key) {
		return this.Nodemap.get(key);

	}

	@Override
	public edge_data getEdge(int src, int dest) {
		try {
			return this.Edgemap.get(src).get(dest);
		}
		catch(NullPointerException e){
			return null;
		}

	}

	@Override
	public void addNode(node_data n) {
		this.Nodemap.put(n.getKey(),n);
		this.nodeSize++;
		this.MC++;
	}

	@Override
	public void connect(int src, int dest, double w) {
		edge_data temp = new Edge(src,dest,w,"",0);
		if(this.getNode(src)!=null && this.getNode(dest)!=null) {  // the nodes isnt exist.
			if (this.getEdge(src, dest) == null) {  // the edge isnt exist.
				if(this.Edgemap.get(src) == null) {   // the Hashmap of neighburs isnt exist
					HashMap<Integer, edge_data> add = new HashMap<>();
					this.Edgemap.put(src,add);
					this.Edgemap.get(src).put(dest,temp);
				}
				else {         //hash map of neighburs exist but the edge isnt.
					this.Edgemap.get(src).put(dest, temp);
				}
			}
		}
		else {
			System.err.println("Wrong input,Missing Nodes.");
			return;
		}
		this.edgeSize++;
		this.MC++;
	}

	@Override
	public Collection<node_data> getV() {
		return this.Nodemap.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		return this.Edgemap.get(node_id).values();
	}

	@Override
	public node_data removeNode(int key) {
		node_data removed = this.getNode(key);
		if(removed!=null){
			this.Nodemap.remove(key);
			this.nodeSize--;
			if(this.Edgemap.get(key)!=null)this.Edgemap.remove(key);
			Iterator iterator = this.Edgemap.entrySet().iterator();
			while(iterator.hasNext()){
				Map.Entry map =(Map.Entry)iterator.next();
				int key2=(int) map.getKey();
				if(this.Edgemap.get(key2).get(key)!=null){
					removeEdge(key2,key);
				}
			}

		}
		return removed;
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		edge_data temp =this.getEdge(src,dest);
		if(temp!=null) {
			this.Edgemap.get(src).remove(dest);
			this.edgeSize--;
			this.MC++;
			return temp ;
		}
		System.err.println("Edge isnt exist, for src:" + src + ",  dest:" + dest);
		return temp;
	}

	@Override
	public int nodeSize() {
		return this.nodeSize;
	}

	@Override
	public int edgeSize() {
		return this.edgeSize;
	}

	@Override
	public int getMC() {
		return this.MC;
	}

	public static void main(String[] args) {
		Point3D x = new Point3D(1,4,0);
		Point3D y = new Point3D(2,5,0);
		Point3D q = new Point3D(4,3,0);
		node_data a = new Node(1,2,3,x,"asf");
		node_data b = new Node(3,4,6,y,"ads");
		node_data c = new Node(5,50,50,q,"sf");
		node_data r = new Node(6,4,50,q,"sf");
		DGraph d = new DGraph();
		d.addNode(a);
		d.addNode(b);
		d.addNode(c);
		d.addNode(r);
		d.connect(r.getKey(),a.getKey(),4);
		d.connect(a.getKey(),b.getKey(),4);
		d.connect(a.getKey(),c.getKey(),50);
		System.out.println(d.getEdge(1,5).getWeight());
		d.removeNode(1);
		d.removeEdge(1,3);
		edge_data t = d.getEdge(1,3);
		System.out.println(d.Edgemap);
		System.out.println(d.Nodemap);
	}

}
