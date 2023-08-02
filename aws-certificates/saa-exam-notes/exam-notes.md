# AWS Solution Architect Associate

## S3

### S3 object

objects on s3 may consist of: key, value, version id, and metadata (additional information about the object)
You can store data from _0 bytes_ tp 5 terabytes in size

### Storage classes

- Standatd: 99.99% availibility, 11 9's durability. Replicated across at least three AZs
- Intelligen tiering
- Standatd IA: Cheeper if you access files less than once a month. Additional retrival fee is applied. Reduced availibility.
- One zone IA: Objects only exist in one AZ. Avalibility is 99,5%. A retrival fee is applied. Reduced durability (data could get destroyed).
- Glacier: Long-term cheap cold storage. Retrival of data can take minutes to hours
- Glacier deep archieve: Data retrival time is 12 hours

![alt text](./images/s3-storage-class.png 'S3 storage class comparision')
