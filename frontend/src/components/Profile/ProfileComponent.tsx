import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Context } from "../Context";
import { getAccountById, updateAccount } from "../../api/accountApi";
import { type User, type Company } from "../../types/Account";
import type { UpdateAccountDTO } from "../../types/updateAccountDTO";
import styles from "./ProfileComponent.module.css";

// Combine optional fields for User and Company
type ProfileState = Partial<User> & Partial<Company>;

export default function ProfileComponent() {
  const { loggedId, accountType } = Context();
  const navigate = useNavigate();

  const [profile, setProfile] = useState<ProfileState>({});
  const [isEditing, setIsEditing] = useState(false);

  useEffect(() => {
    const fetchProfile = async () => {
      if (!loggedId) return;

      try {
        const data = await getAccountById(loggedId);
        setProfile(data);
      } catch (err) {
        console.error("Failed to fetch profile:", err);
      }
    };

    fetchProfile();
  }, [loggedId]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setProfile({ ...profile, [e.target.name]: e.target.value });
  };

  const handleSave = async () => {
    if (!loggedId) return;

    try {
      const dto: UpdateAccountDTO = {
        email: profile.email || "",
        description: profile.description || "",
        firstName: accountType === "USER" ? profile.firstName || "" : "",
        lastName: accountType === "USER" ? profile.lastName || "" : "",
        companyName: accountType === "COMPANY" ? profile.companyName || "" : "",
        city: accountType === "COMPANY" ? profile.city || "" : "",
        street: accountType === "COMPANY" ? profile.street || "" : "",
        number: accountType === "COMPANY" ? profile.number || "" : "",
      };

      await updateAccount(loggedId, dto);
      setIsEditing(false);
    } catch (err) {
      console.error("Failed to update profile:", err);
    }
  };

  const handleBack = () => {
    navigate(-1); // go to previous page
  };

  if (!loggedId || Object.keys(profile).length === 0) return <p>Loading...</p>;

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>
        {accountType === "USER" ? "User Profile" : "Company Profile"}
      </h1>

      {/* COMMON FIELDS */}
      <div className={styles.field}>
        <label>Email:</label>
        {isEditing ? (
          <input name="email" value={profile.email || ""} onChange={handleChange} />
        ) : (
          <p>{profile.email}</p>
        )}
      </div>

      <div className={styles.field}>
        <label>Description:</label>
        {isEditing ? (
          <input name="description" value={profile.description || ""} onChange={handleChange} />
        ) : (
          <p>{profile.description}</p>
        )}
      </div>

      {/* USER FIELDS */}
      {accountType === "USER" && (
        <>
          <div className={styles.field}>
            <label>First Name:</label>
            {isEditing ? (
              <input name="firstName" value={profile.firstName || ""} onChange={handleChange} />
            ) : (
              <p>{profile.firstName}</p>
            )}
          </div>

          <div className={styles.field}>
            <label>Last Name:</label>
            {isEditing ? (
              <input name="lastName" value={profile.lastName || ""} onChange={handleChange} />
            ) : (
              <p>{profile.lastName}</p>
            )}
          </div>
        </>
      )}

      {/* COMPANY FIELDS */}
      {accountType === "COMPANY" && (
        <>
          <div className={styles.field}>
            <label>Company Name:</label>
            {isEditing ? (
              <input
                name="companyName"
                value={profile.companyName || ""}
                onChange={handleChange}
              />
            ) : (
              <p>{profile.companyName}</p>
            )}
          </div>

          <div className={styles.field}>
            <label>City:</label>
            {isEditing ? (
              <input name="city" value={profile.city || ""} onChange={handleChange} />
            ) : (
              <p>{profile.city}</p>
            )}
          </div>

          <div className={styles.field}>
            <label>Street:</label>
            {isEditing ? (
              <input name="street" value={profile.street || ""} onChange={handleChange} />
            ) : (
              <p>{profile.street}</p>
            )}
          </div>

          <div className={styles.field}>
            <label>Number:</label>
            {isEditing ? (
              <input name="number" value={profile.number || ""} onChange={handleChange} />
            ) : (
              <p>{profile.number}</p>
            )}
          </div>
        </>
      )}

      {/* Buttons */}
      <div className={styles.buttons}>
        <button className={`${styles.button} ${styles.back}`} onClick={handleBack}>
          Back
        </button>
        {isEditing ? (
          <button className={`${styles.button} ${styles.save}`} onClick={handleSave}>
            Save
          </button>
        ) : (
          <button className={`${styles.button} ${styles.edit}`} onClick={() => setIsEditing(true)}>
            Edit
          </button>
        )}
      </div>
    </div>
  );
}