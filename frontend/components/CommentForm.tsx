"use client";

import { useState } from "react";
import { addCommentToPost } from "../lib/api";

interface CommentFormProps {
  postId: string;
  onCommentAdded: () => void;
}

export default function CommentForm({ postId, onCommentAdded }: CommentFormProps) {
  const [content, setContent] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setLoading(true);

    try {
      await addCommentToPost(postId, { content });
      setContent("");
      onCommentAdded();
    } catch (err: any) {
      setError(err.message || "Błąd podczas dodawania komentarza.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="mt-4">
      {error && <p className="text-red-500 text-sm mb-2">{error}</p>}
      <textarea
        className="w-full p-2 border border-gray-300 rounded-md mb-2 focus:outline-none focus:ring-emerald-500 focus:border-emerald-500 text-gray-900"
        placeholder="Dodaj komentarz..."
        value={content}
        onChange={(e) => setContent(e.target.value)}
        rows={2}
        required
      ></textarea>
      <button
        type="submit"
        className="w-full py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-emerald-600 hover:bg-emerald-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-emerald-500"
        disabled={loading}
      >
        {loading ? "Dodawanie..." : "Skomentuj"}
      </button>
    </form>
  );
}