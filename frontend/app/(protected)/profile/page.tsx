"use client";

import { useEffect, useState, useCallback } from "react";
import ProtectedLayout from "@/components/ProtectedLayout";
import { getMyProfile } from "@/lib/api";
import { UserProfile } from "@/lib/types";
import ProfileEditForm from "@/components/ProfileEditForm";

export default function ProfilePage() {
  const [profile, setProfile] = useState<UserProfile | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [isEditing, setIsEditing] = useState(false);

  const fetchProfile = useCallback(async () => {
    try {
      const data = await getMyProfile();
      setProfile(data);
    } catch (err: any) {
      setError(err.message || "Nie udało się pobrać profilu.");
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchProfile();
  }, [fetchProfile]);

  const handleUpdateSuccess = () => {
    setIsEditing(false);
    fetchProfile();
  };

  if (loading) {
    return (
      <ProtectedLayout>
        <div className="flex justify-center p-6">Ładowanie profilu...</div>
      </ProtectedLayout>
    );
  }

  if (error || !profile) {
    return (
      <ProtectedLayout>
        <div className="flex justify-center p-6 text-red-500">
          {error || "Profil niedostępny."}
        </div>
      </ProtectedLayout>
    );
  }

  return (
    <ProtectedLayout>
      <div className="max-w-2xl mx-auto p-6 bg-white rounded-lg shadow-md mt-6">
        {isEditing ? (
          <>
            <h2 className="text-2xl font-bold mb-4">Edycja Profilu</h2>
            <ProfileEditForm
              user={profile}
              onUpdateSuccess={handleUpdateSuccess}
              onCancel={() => setIsEditing(false)}
            />
          </>
        ) : (
          <>
            <div className="flex items-center space-x-4 mb-6">
              <div className="w-20 h-20 bg-gray-300 rounded-full flex items-center justify-center text-3xl text-gray-600">
                {profile.username.charAt(0).toUpperCase()}
              </div>
              <div>
                <h1 className="text-2xl font-bold text-gray-800">{profile.username}</h1>
                <p className="text-gray-600">{profile.email}</p>
              </div>
            </div>

            <div className="flex justify-around border-t border-b border-gray-200 py-4 mb-6">
              <div className="text-center">
                <span className="block font-bold text-lg text-gray-800">
                  {profile.followersCount}
                </span>
                <span className="text-sm text-gray-500">Obserwujących</span>
              </div>
              <div className="text-center">
                <span className="block font-bold text-lg text-gray-800">
                  {profile.followingCount}
                </span>
                <span className="text-sm text-gray-500">Obserwuje</span>
              </div>
            </div>

            <div className="text-center">
              <button
                onClick={() => setIsEditing(true)}
                className="px-4 py-2 bg-emerald-600 text-white rounded-md hover:bg-emerald-700 transition-colors cursor-pointer"
              >
                Edytuj Profil
              </button>
            </div>
          </>
        )}
      </div>
    </ProtectedLayout>
  );
}