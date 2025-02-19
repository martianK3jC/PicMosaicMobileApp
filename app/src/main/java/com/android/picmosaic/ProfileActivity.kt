package com.android.picmosaic

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.EditText
import de.hdodenhof.circleimageview.CircleImageView

class ProfileActivity : Activity() {
    private lateinit var profileImage: CircleImageView
    private lateinit var editProfilePicture: ImageButton
    private lateinit var backButton: ImageButton
    private lateinit var settingsButton: ImageButton
    private lateinit var editProfileButton: android.widget.Button
    private lateinit var logoutButton: android.widget.Button
    private lateinit var saveButton: android.widget.Button
    
    private lateinit var usernameText: TextView
    private lateinit var firstNameText: TextView
    private lateinit var lastNameText: TextView
    private lateinit var emailText: TextView
    private lateinit var phoneText: TextView
    private lateinit var addressText: TextView
    private lateinit var cityText: TextView

    private lateinit var firstNameEdit: EditText
    private lateinit var lastNameEdit: EditText
    private lateinit var phoneEdit: EditText
    private lateinit var addressEdit: EditText
    private lateinit var cityEdit: EditText

    private var currentUserProfile: UserProfile? = null
    private var isInEditMode = false

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Check if user is logged in
        val currentUserEmail = getSharedPreferences("PicMosaic", MODE_PRIVATE)
            .getString("current_user_email", null)
        
        if (currentUserEmail == null) {
            // User is not logged in, redirect to login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.profile_page)
        initializeViews()
        setupClickListeners()
        loadUserData()
    }

    private fun initializeViews() {
        profileImage = findViewById(R.id.profile_image)
        editProfilePicture = findViewById(R.id.edit_profile_picture)
        backButton = findViewById(R.id.arrow_back_button)
        settingsButton = findViewById(R.id.settings_button)
        editProfileButton = findViewById(R.id.edit_profile_button)
        logoutButton = findViewById(R.id.logout_button)
        
        usernameText = findViewById(R.id.profile_username)
        firstNameText = findViewById(R.id.profile_first_name)
        lastNameText = findViewById(R.id.profile_last_name)
        emailText = findViewById(R.id.profile_email)
        phoneText = findViewById(R.id.profile_phone)
        addressText = findViewById(R.id.profile_address)
        cityText = findViewById(R.id.profile_city)
    }



    private fun setupClickListeners() {
        backButton.setOnClickListener {
            if (isInEditMode) {
                if(!hasChanges()){
                    finish()
                }
                showDiscardChangesDialog()
            } else {
                finish()
            }
        }

        editProfilePicture.setOnClickListener {
            openImagePicker()
        }

        editProfileButton.setOnClickListener {
            isInEditMode = true
            setContentView(R.layout.edit_profile)

            initializeEditViews()
            loadDataIntoEditFields()
        }

        logoutButton.setOnClickListener {
            showLogoutDialog()
        }

        settingsButtonClicked()
    }

    private fun settingsButtonClicked() {
        settingsButton.setOnClickListener {
            // Navigate to settings
            startActivity(Intent(this, DummySettingsActivty::class.java))
        }
    }



    private fun initializeEditViews() {
        // Initialize views from edit_profile layout
        profileImage = findViewById(R.id.profile_image)
        editProfilePicture = findViewById(R.id.edit_profile_picture)
        backButton = findViewById(R.id.arrow_back_button)
        settingsButton = findViewById(R.id.settings_button)
        saveButton = findViewById(R.id.save_button)
        usernameText = findViewById(R.id.profile_username)

        firstNameEdit = findViewById(R.id.first_name_edit)
        lastNameEdit = findViewById(R.id.last_name_edit)
        phoneEdit = findViewById(R.id.phone_edit)
        addressEdit = findViewById(R.id.address_edit)
        cityEdit = findViewById(R.id.city_edit)

        // Set up click listeners for edit mode
        backButton.setOnClickListener {
            showDiscardChangesDialog()
        }

        editProfilePicture.setOnClickListener {
            openImagePicker()
        }

        saveButton.setOnClickListener {
            showSaveChangesDialog()
        }

        settingsButton = findViewById(R.id.settings_button)
        settingsButtonClicked()

    }

    private fun loadDataIntoEditFields() {
        currentUserProfile?.let { profile ->
            usernameText.text = profile.firstName
            firstNameEdit.setText(profile.firstName)
            lastNameEdit.setText(profile.lastName)
            phoneEdit.setText(profile.phone)
            addressEdit.setText(profile.address)
            cityEdit.setText(profile.city)
        }
    }

    private fun showSaveChangesDialog() {
        if (!hasChanges()) {
            Toast.makeText(this, "No changes made", Toast.LENGTH_SHORT).show()
            return
        }

        AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .setTitle("Save Changes?")
            .setMessage("Do you want to save the changes made to your profile?")
            .setPositiveButton("Yes") { _, _ -> saveUserData() }
            .setNegativeButton("No", null)
            .show()
    }


    private fun showDiscardChangesDialog() {
        AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .setTitle("Discard Changes?")
            .setMessage("Are you sure you want to discard your changes?")
            .setPositiveButton("Yes") { _, _ ->
                isInEditMode = false
                setContentView(R.layout.profile_page)
                initializeViews()
                setupClickListeners()
                loadUserData()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                // Clear current user
                getSharedPreferences("PicMosaic", MODE_PRIVATE).edit()
                    .remove("current_user_email")
                    .apply()
                
                // Navigate to login
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun hasChanges(): Boolean {
        return currentUserProfile?.let { profile ->
            firstNameEdit.text.toString() != profile.firstName ||
            lastNameEdit.text.toString() != profile.lastName ||
            phoneEdit.text.toString() != profile.phone ||
            addressEdit.text.toString() != profile.address ||
            cityEdit.text.toString() != profile.city
        } ?: false
    }


    private fun saveUserData() {
        val email = getSharedPreferences("PicMosaic", MODE_PRIVATE)
            .getString("current_user_email", "") ?: ""
            
        currentUserProfile = currentUserProfile?.copy(
            firstName = firstNameEdit.text?.toString() ?: "",
            lastName = lastNameEdit.text?.toString() ?: "",
            phone = phoneEdit.text?.toString() ?: "",
            address = addressEdit.text?.toString() ?: "",
            city = cityEdit.text?.toString() ?: ""
        )

        currentUserProfile?.let { profile ->
            // Update the dummy data
            DummyUserData.updateUserProfile(email, profile)
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
            
            // Return to profile view
            isInEditMode = false
            setContentView(R.layout.profile_page)
            initializeViews()
            setupClickListeners()
            loadUserData()
        }
    }

    private fun loadUserData() {
        val email = getSharedPreferences("PicMosaic", MODE_PRIVATE)
            .getString("current_user_email", "") ?: ""
            
        currentUserProfile = DummyUserData.getUserProfile(email)
        
        currentUserProfile?.let { profile ->
            usernameText.text = profile.firstName
            firstNameText.text = profile.firstName
            lastNameText.text = profile.lastName
            emailText.text = email
            phoneText.text = profile.phone
            addressText.text = profile.address
            cityText.text = profile.city
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            profileImage.setImageURI(imageUri)
        }
    }
}