"use client";

import { useState } from "react";
import ProtectedLayout from "@/components/ProtectedLayout";
import PostList from "@/components/PostList";
import CreatePostForm from "@/components/CreatePostForm";

export default function DashboardPage() {
  const [refreshPosts, setRefreshPosts] = useState(false);

  const handlePostCreated = () => {
    setRefreshPosts((prev) => !prev);
  };

  return (
    <ProtectedLayout>
      <div className="flex flex-col items-center p-6 w-full max-w-[800px] mx-auto space-y-6">
        <h1 className="text-4xl font-light text-gray-600 mb-4">
          Witaj w Dailsy!
        </h1>
        <div className="w-full">
          <CreatePostForm onPostCreated={handlePostCreated} />
        </div>
        <div className="w-full">
          <PostList key={refreshPosts ? "refresh" : "no-refresh"} />
        </div>
      </div>
    </ProtectedLayout>
  );
}