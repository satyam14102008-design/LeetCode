class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {
        int n = servers.length;
        int m = tasks.length;
        int[] ans = new int[m];
        
        // Free servers: [weight, index]
        PriorityQueue<int[]> free = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            return a[1] - b[1];
        });
        
        // Busy servers: [freeTime, weight, index]
        PriorityQueue<int[]> busy = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            if (a[1] != b[1]) return a[1] - b[1];
            return a[2] - b[2];
        });
        
        for (int i = 0; i < n; i++) {
            free.offer(new int[]{servers[i], i});
        }
        
        Deque<Integer> queue = new ArrayDeque<>();
        int taskIdx = 0;
        int time = 0;
        
        while (taskIdx < m || !queue.isEmpty()) {
            // Add tasks that arrive at current time
            while (taskIdx < m && taskIdx <= time) {
                queue.offer(taskIdx++);
            }
            
            // Release servers that finished by current time
            while (!busy.isEmpty() && busy.peek()[0] <= time) {
                int[] s = busy.poll();
                free.offer(new int[]{s[1], s[2]});
            }
            
            // Assign as many tasks as possible
            while (!queue.isEmpty() && !free.isEmpty()) {
                int task = queue.poll();
                int[] s = free.poll();
                ans[task] = s[1];
                busy.offer(new int[]{time + tasks[task], s[0], s[1]});
            }
            
            // Advance time intelligently
            if (taskIdx < m || !queue.isEmpty()) {
                if (!queue.isEmpty() && free.isEmpty()) {
                    // Must wait for a server to free up
                    time = busy.peek()[0];
                } else {
                    // Either queue is empty (wait for next task) or we can process at next second
                    time++;
                }
            }
        }
        
        return ans;
    }
}