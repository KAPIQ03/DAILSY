"use client";

import { useEffect, useState } from "react";
import { getFeedPosts } from "../lib/api";
import { Post } from "../lib/types";
import PostCard from "./PostCard";

export default function PostList() {
  const [posts, setPosts] = useState<Post[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const data = await getFeedPosts();
        if (Array.isArray(data)) {
          setPosts(data);
        }
      } catch (err: any) {
        setError(err.message || "Błąd podczas ładowania postów.");
      } finally {
        setLoading(false);
      }
    };

    fetchPosts();
  }, []);

  if (loading) {
    return <div className="text-center text-gray-600">Ładowanie postów...</div>;
  }

  if (error) {
    return <div className="text-center text-red-500">Błąd: {error}</div>;
  }

  return (
    <div className="space-y-4">
      {posts.length > 0 ? (
        [...posts].sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()).map((post) => <PostCard key={post.id} post={post} />)
      ) : (
        <div className="text-center text-gray-600">Brak postów do wyświetlenia.</div>
      )}
    </div>
  );
}