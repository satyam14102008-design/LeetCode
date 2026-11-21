class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        
        List<List<Integer>> children = new ArrayList<>();
        for (int i = 0; i < n; i++) children.add(new ArrayList<>());
        for (int i = 1; i < n; i++) children.get(parent[i]).add(i);
        
        int[] newParent = parent.clone();
        int[] lastSeen = new int[26];
        Arrays.fill(lastSeen, -1);
        
        // stack entries: {node, oldVal, isEnter}  isEnter: 1 = enter, 0 = exit
        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{0, -1, 1});
        while (!stack.isEmpty()) {
            int[] top = stack.pop();
            int node = top[0], oldVal = top[1], isEnter = top[2];
            int c = s.charAt(node) - 'a';
            if (isEnter == 1) {
                if (lastSeen[c] != -1) newParent[node] = lastSeen[c];
                stack.push(new int[]{node, lastSeen[c], 0});
                lastSeen[c] = node;
                for (int child : children.get(node)) {
                    stack.push(new int[]{child, -1, 1});
                }
            } else {
                lastSeen[c] = oldVal;
            }
        }
        
        List<List<Integer>> finalChildren = new ArrayList<>();
        for (int i = 0; i < n; i++) finalChildren.add(new ArrayList<>());
        for (int i = 1; i < n; i++) finalChildren.get(newParent[i]).add(i);
        
        List<Integer> order = new ArrayList<>();
        Deque<Integer> st = new ArrayDeque<>();
        st.push(0);
        while (!st.isEmpty()) {
            int node = st.pop();
            order.add(node);
            for (int child : finalChildren.get(node)) st.push(child);
        }
        
        int[] size = new int[n];
        Arrays.fill(size, 1);
        for (int i = order.size() - 1; i >= 0; i--) {
            int node = order.get(i);
            if (node != 0) size[newParent[node]] += size[node];
        }
        
        return size;
    }
}