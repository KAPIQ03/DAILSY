"use client";

import { useEffect, useState } from "react";
import { getCommentsForPost } from "../lib/api";
import { Comment } from "../lib/types";

interface CommentListProps {
  postId: string;
}

export default function CommentList({ postId }: CommentListProps) {
  const [comments, setComments] = useState<Comment[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchComments = async () => {
      try {
        const data = await getCommentsForPost(postId);
        if (Array.isArray(data)) {
          setComments(data);
        }
      } catch (err: any) {
        setError(err.message || "Błąd podczas ładowania komentarzy.");
      } finally {
        setLoading(false);
      }
    };

    fetchComments();
  }, [postId]);

  if (loading) {
    return <div className="text-center text-gray-600">Ładowanie komentarzy...</div>;
  }

  if (error) {
    return <div className="text-center text-red-500">Błąd: {error}</div>;
  }

  return (
    <div className="mt-4 border-t border-gray-200 pt-4">
      <h3 className="text-lg font-semibold text-gray-700 mb-2">Komentarze</h3>
      {comments.length > 0 ? (
        comments.map((comment) => (
          <div key={comment.id} className="bg-gray-50 p-3 rounded-lg mb-2">
            <div className="font-bold text-gray-800">{comment.authorUsername}</div>
            <p className="text-gray-700">{comment.content}</p>
            <div className="text-gray-500 text-sm mt-1">
              {new Date(comment.createdAt).toLocaleString()}
            </div>
          </div>
        ))
      ) : (
        <div className="text-gray-600">Brak komentarzy. Bądź pierwszy!</div>
      )}
    </div>
  );
}