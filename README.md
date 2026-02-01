# career-tracker
Career Tracker is a full-stack web app that helps users organize and monitor their job applications. It lets users record roles, companies, statuses, and notes, track progress through hiring stages, and review their activity using a clear, simple interface powered by a Springboot API, PostgreSQL, Redis(for caching) and React for frontend. Docker will be used for deployment.


# COMMANDS

./gradlew bootRun
./gradlew clean build
linux: export $(cat .env | xargs)

windows:
Get-Content .env | ForEach-Object {
    if ($_ -and $_ -notmatch '^#') {
        $name, $value = $_ -split '=', 2
        Set-Item -Path "Env:$name" -Value $value
    }
}

docker buildx build --platform linux/amd64 -t loca0307/careertracker --push .

##  Features

###  Job Application Management
- Create a job application  
- View list of applications  
- View details of a single application  
- Update application (status, notes, etc.)  
- Delete application  

Each application stores:
- Company  
- Role / Position  
- Application date  
- Status (Applied, Interview, Offer, Rejected, etc.)  
- Notes  

---

###  Status Progress Tracking
- Track movement through hiring stages  
- Log every status change with:
  - Date  
  - New status  
  - Optional comment  
- Generates a timeline per application  

---

###  Contact Management (Recruiters / Interviewers)
- Add contacts related to an application  
- Store:
  - Name  
  - Email  
  - Role  
  - Phone  
  - Notes  
- Multiple contacts can be linked to the same application  

---

###  Activity / Follow-Up Reminders
- Add reminders (e.g., “Follow up in 7 days”)  
- Due date + description  
- Mark reminders as completed  

---

###  Tags / Categories (optional)
- Create tags such as: internship, remote, backend, graduate  
- Assign multiple tags to a single application  

---

###  Dashboard & Insights
- Count applications by status  
- Applications over time  
- (Optional) Average time spent per hiring stage  
- **Redis caching** may be used to speed up aggregate queries  

---

## Project Goals

This project is designed for:
- Learning full-stack development with modern tools  
- Practicing clean architecture and REST API design  
- Demonstrating real-world portfolio skills  

---

## Roadmap (Next Improvements)

- Authentication / user accounts  
- File uploads (CV, cover letter, contracts)  
- Email reminders  
- Export to CSV / PDF  

---

## License

This project is intended for learning and portfolio use. You may choose and add a license (MIT recommended).

---

## Run the project

./gradlew bootRun